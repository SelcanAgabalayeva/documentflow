package com.selcan.documentflow.dtos.approval;

import com.selcan.documentflow.enums.ApprovalDecision;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ApprovalResponseDto {

    private Long id;
    private String comment;
    private ApprovalDecision decision;
    private LocalDateTime decisionAt;
    private String approverEmail;
    private Long documentId;
}
