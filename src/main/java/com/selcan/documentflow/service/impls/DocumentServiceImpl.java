package com.selcan.documentflow.service.impls;

import com.selcan.documentflow.congigswager.FileStorageService;
import com.selcan.documentflow.dtos.documents.DocumentCreateDto;
import com.selcan.documentflow.dtos.documents.DocumentResponseDto;
import com.selcan.documentflow.dtos.documents.DocumentSubmitMessage;
import com.selcan.documentflow.entity.Approval;
import com.selcan.documentflow.entity.Document;
import com.selcan.documentflow.entity.User;
import com.selcan.documentflow.enums.DocumentStatus;
import com.selcan.documentflow.enums.Role;
import com.selcan.documentflow.enums.WorkflowAction;
import com.selcan.documentflow.exceptions.UserNotFoundException;
import com.selcan.documentflow.integration.gateway.WorkflowGateway;
import com.selcan.documentflow.repositories.ApprovalRepository;
import com.selcan.documentflow.repositories.DocumentRepository;
import com.selcan.documentflow.repositories.UserRepository;
import com.selcan.documentflow.service.AuditService;
import com.selcan.documentflow.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final AuditService auditService;
    private final DocumentWorkflowEngine workflowEngine;
    private final WorkflowGateway workflowGateway;
    private final FileStorageService fileStorageService;
    private final ApprovalRepository approvalRepository;


    @Override
    public DocumentResponseDto upload(DocumentCreateDto dto, String username) {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        User approver = userRepository.findByRole(Role.APPROVER)
                .stream()
                .findAny()
                .orElseThrow(() -> new RuntimeException("No approver found"));

        String savedFilePath = fileStorageService.saveFile(dto.getFile());

        Document doc = new Document();
        doc.setTitle(dto.getTitle());
        doc.setDescription(dto.getDescription());
        doc.setFilePath(savedFilePath);
        doc.setStatus(DocumentStatus.SUBMITTED);
        doc.setCreatedAt(LocalDateTime.now());
        doc.setCreatedBy(user);

        documentRepository.save(doc);

        Approval approval = new Approval();
        approval.setDocument(doc);
        approval.setApprover(approver);
        approval.setDecision(null);
        approvalRepository.save(approval);

        auditService.log("DOCUMENT_CREATED", username, doc.getId());

        workflowGateway.submitDocument(
                new DocumentSubmitMessage(doc.getId(), username)
        );

        return map(doc);
    }




    @Override
    public void approve(Long documentId, String approverEmail) {

        workflowEngine.changeState(
                documentId,
                WorkflowAction.APPROVE,
                approverEmail
        );
    }

    @Override
    public void reject(Long documentId, String approverEmail) {
        workflowEngine.changeState(
                documentId,
                WorkflowAction.REJECT,
                approverEmail
        );
    }

    @Override
    public @Nullable List<DocumentResponseDto> getMyDocuments(String name) {

        List<Document> documents = documentRepository.findByCreatedByEmail(name);

        return documents.stream()
                .map(this::map)
                .toList();
    }
    @Override
    public List<DocumentResponseDto> getPendingApprovals(String email) {

        List<Document> documents =
                approvalRepository.findPendingDocumentsForApprover(email);

        return documents.stream()
                .map(this::map)
                .toList();
    }

    @Override
    public DocumentResponseDto getById(Long id) {
        Document doc = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        return map(doc);
    }

    @Override
    public void delete(Long id) {
        documentRepository.deleteById(id);
    }

    private DocumentResponseDto map(Document doc) {
        return DocumentResponseDto.builder()
                .id(doc.getId())
                .title(doc.getTitle())
                .description(doc.getDescription())
                .filePath(doc.getFilePath())
                .status(doc.getStatus().name())
                .createdAt(doc.getCreatedAt())
                .createdBy(doc.getCreatedBy().getEmail())
                .build();
    }
}
