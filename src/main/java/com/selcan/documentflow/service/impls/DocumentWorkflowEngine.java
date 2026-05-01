package com.selcan.documentflow.service.impls;

import com.selcan.documentflow.entity.Document;
import com.selcan.documentflow.enums.DocumentStatus;
import com.selcan.documentflow.enums.WorkflowAction;
import com.selcan.documentflow.repositories.DocumentRepository;
import com.selcan.documentflow.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DocumentWorkflowEngine {

    private final DocumentRepository documentRepository;
    private final AuditService auditService;

    public Document changeState(Long documentId,
                                WorkflowAction action,
                                String performedBy) {

        Document doc = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        DocumentStatus newStatus = calculateNextStatus(doc.getStatus(), action);

        doc.setStatus(newStatus);
        doc.setUpdatedAt(LocalDateTime.now());

        documentRepository.save(doc);

        auditService.log(
                "WORKFLOW_" + action.name(),
                performedBy,
                documentId
        );

        return doc;
    }

    private DocumentStatus calculateNextStatus(DocumentStatus current,
                                               WorkflowAction action) {

        return switch (current) {

            case DRAFT -> {
                if (action == WorkflowAction.SUBMIT)
                    yield DocumentStatus.SUBMITTED;
                yield current;
            }

            case SUBMITTED -> {
                if (action == WorkflowAction.SEND_FOR_APPROVAL)
                    yield DocumentStatus.PENDING_APPROVAL;
                yield current;
            }

            case PENDING_APPROVAL -> {
                if (action == WorkflowAction.APPROVE)
                    yield DocumentStatus.APPROVED;

                if (action == WorkflowAction.REJECT)
                    yield DocumentStatus.REJECTED;

                yield current;
            }

            case APPROVED -> {
                if (action == WorkflowAction.ARCHIVE)
                    yield DocumentStatus.ARCHIVED;
                yield current;
            }

            default -> current;
        };
    }
}
