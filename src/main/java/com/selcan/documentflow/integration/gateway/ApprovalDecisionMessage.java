package com.selcan.documentflow.integration.gateway;


import com.selcan.documentflow.enums.ApprovalDecision;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalDecisionMessage {
    private Long documentId;
    private ApprovalDecision decision;
    private String approverEmail;
}
