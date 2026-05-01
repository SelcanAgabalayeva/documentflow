package com.selcan.documentflow.dtos.approval;

import com.selcan.documentflow.enums.ApprovalDecision;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApprovalRequestDto {

    @NotNull
    private Long documentId;

    @NotNull
    private ApprovalDecision decision;

    private String comment;
}
