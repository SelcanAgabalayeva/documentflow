package com.selcan.documentflow.integration.handler;

import com.selcan.documentflow.enums.WorkflowAction;
import com.selcan.documentflow.integration.gateway.ApprovalDecisionMessage;
import com.selcan.documentflow.service.impls.DocumentWorkflowEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class WorkflowMessageHandler {

    private final DocumentWorkflowEngine workflowEngine;

    public void handleSubmission(Long documentId) {

        workflowEngine.changeState(
                documentId,
                WorkflowAction.SUBMIT,
                "system"
        );

        workflowEngine.changeState(
                documentId,
                WorkflowAction.SEND_FOR_APPROVAL,
                "system"
        );
    }

    public void handleApprovalDecision(ApprovalDecisionMessage message) {

        WorkflowAction action =
                message.getDecision().name().equals("APPROVED")
                        ? WorkflowAction.APPROVE
                        : WorkflowAction.REJECT;

        workflowEngine.changeState(
                message.getDocumentId(),
                action,
                message.getApproverEmail()
        );
    }
}