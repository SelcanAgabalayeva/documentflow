package com.selcan.documentflow.integration.gateway;


import com.selcan.documentflow.dtos.documents.DocumentSubmitMessage;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface WorkflowGateway {

    @Gateway(requestChannel = "documentSubmissionChannel")
    void submitDocument(DocumentSubmitMessage message);

    @Gateway(requestChannel = "approvalDecisionChannel")
    void processApproval(ApprovalDecisionMessage message);

}
