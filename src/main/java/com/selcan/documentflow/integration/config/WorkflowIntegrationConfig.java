package com.selcan.documentflow.integration.config;


import com.selcan.documentflow.dtos.documents.DocumentSubmitMessage;
import com.selcan.documentflow.entity.Document;
import com.selcan.documentflow.entity.User;
import com.selcan.documentflow.enums.Role;
import com.selcan.documentflow.integration.gateway.ApprovalDecisionMessage;
import com.selcan.documentflow.integration.handler.WorkflowMessageHandler;
import com.selcan.documentflow.integration.service.MailNotificationService;
import com.selcan.documentflow.repositories.DocumentRepository;
import com.selcan.documentflow.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.handler.advice.RequestHandlerRetryAdvice;

@Configuration
@RequiredArgsConstructor
public class WorkflowIntegrationConfig {

    private final WorkflowMessageHandler workflowMessageHandler;
    private final RequestHandlerRetryAdvice retryAdvice;
    private final MailNotificationService mailNotificationService;
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    @Bean
    public IntegrationFlow documentSubmissionFlow() {
        return IntegrationFlow
                .from("documentSubmissionChannel")
                .handle(DocumentSubmitMessage.class, (payload, headers) -> {
                    workflowMessageHandler.handleSubmission(payload.getDocumentId());
                    return payload;
                })
                .handle(DocumentSubmitMessage.class, (payload, headers) -> {

                    Document doc = documentRepository.findById(payload.getDocumentId())
                            .orElseThrow(() -> new RuntimeException("Document not found"));

                    User approver = userRepository.findFirstByRole(Role.APPROVER)
                            .orElseThrow(() -> new RuntimeException("Approver not found"));

                    mailNotificationService.sendApprovalRequest(doc, approver.getEmail());
                    return null;
                }, e -> e.advice(retryAdvice))
                .get();
    }

    @Bean
    public IntegrationFlow approvalDecisionFlow() {
        return IntegrationFlow
                .from("approvalDecisionChannel")
                .handle(ApprovalDecisionMessage.class, (payload, headers) -> {
                    workflowMessageHandler.handleApprovalDecision(payload);
                    return null;
                })
                .get();
    }
}
