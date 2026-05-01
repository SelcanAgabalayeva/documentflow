package com.selcan.documentflow.service;

import com.selcan.documentflow.dtos.approval.ApprovalRequestDto;
import com.selcan.documentflow.dtos.approval.ApprovalResponseDto;

import java.util.List;

public interface ApprovalService {

    ApprovalResponseDto approve(ApprovalRequestDto dto, String approverEmail);

    List<ApprovalResponseDto> getByDocument(Long documentId);
}
