package com.selcan.documentflow.service.impls;

import com.selcan.documentflow.dtos.approval.ApprovalRequestDto;
import com.selcan.documentflow.dtos.approval.ApprovalResponseDto;
import com.selcan.documentflow.entity.Approval;
import com.selcan.documentflow.entity.Document;
import com.selcan.documentflow.entity.User;
import com.selcan.documentflow.enums.ApprovalDecision;
import com.selcan.documentflow.enums.DocumentStatus;
import com.selcan.documentflow.integration.gateway.ApprovalDecisionMessage;
import com.selcan.documentflow.integration.gateway.WorkflowGateway;
import com.selcan.documentflow.repositories.ApprovalRepository;
import com.selcan.documentflow.repositories.DocumentRepository;
import com.selcan.documentflow.repositories.UserRepository;
import com.selcan.documentflow.service.ApprovalService;
import com.selcan.documentflow.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final WorkflowGateway workflowGateway;
    private final AuditService auditService;

    @Transactional
    public ApprovalResponseDto approve(ApprovalRequestDto dto, String email) {

        Document doc = documentRepository.findById(dto.getDocumentId())
                .orElseThrow();

        User approver = userRepository.findByEmail(email)
                .orElseThrow();

        Approval approval = approvalRepository
                .findByDocument_IdAndApprover_Email(dto.getDocumentId(), email)
                .orElseThrow(() -> new RuntimeException("Approval not found"));

        approval.setDecision(dto.getDecision());
        approval.setComment(dto.getComment());
        approval.setDecisionAt(LocalDateTime.now());

        approvalRepository.save(approval);
        doc.setStatus(
                dto.getDecision() == ApprovalDecision.APPROVED
                        ? DocumentStatus.APPROVED
                        : DocumentStatus.REJECTED
        );

        documentRepository.save(doc);

        auditService.log("APPROVAL_UPDATED", email, doc.getId());

        workflowGateway.processApproval(
                new ApprovalDecisionMessage(
                        dto.getDocumentId(),
                        dto.getDecision(),
                        email
                )
        );

        return map(approval);
    }
    @Override
    public List<ApprovalResponseDto> getByDocument(Long documentId) {
        return approvalRepository.findByDocumentId(documentId)
                .stream()
                .map(this::map)
                .toList();
    }

    private ApprovalResponseDto map(Approval a) {
        return ApprovalResponseDto.builder()
                .id(a.getId())
                .comment(a.getComment())
                .decision(a.getDecision())
                .decisionAt(a.getDecisionAt())
                .approverEmail(a.getApprover().getEmail())
                .documentId(a.getDocument().getId())
                .build();
    }
}
