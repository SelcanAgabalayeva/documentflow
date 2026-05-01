package com.selcan.documentflow.dtos.audit;

import com.selcan.documentflow.dtos.AuditLogDto;
import com.selcan.documentflow.dtos.approval.ApprovalResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class DocumentWorkflowDto {

    private Long documentId;
    private String currentStatus;
    private String createdBy;
    private List<ApprovalResponseDto> approvals;
    private List<AuditLogDto> auditLogs;
}
