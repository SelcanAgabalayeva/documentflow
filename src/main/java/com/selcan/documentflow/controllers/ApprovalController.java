package com.selcan.documentflow.controllers;

import com.selcan.documentflow.dtos.approval.ApprovalRequestDto;
import com.selcan.documentflow.dtos.approval.ApprovalResponseDto;
import com.selcan.documentflow.dtos.documents.DocumentResponseDto;
import com.selcan.documentflow.entity.User;
import com.selcan.documentflow.integration.gateway.ApprovalDecisionMessage;
import com.selcan.documentflow.integration.gateway.WorkflowGateway;
import com.selcan.documentflow.service.ApprovalService;
import com.selcan.documentflow.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/approvals")
@RequiredArgsConstructor
public class ApprovalController {
    private final ApprovalService approvalService;
    private final DocumentService documentService;
    @PreAuthorize("hasRole('APPROVER')")
    @GetMapping("/pending-approvals")
    public ResponseEntity<List<DocumentResponseDto>> pendingApprovals(Authentication authentication) {
        return ResponseEntity.ok(documentService.getPendingApprovals(authentication.getName()));
    }
    @PreAuthorize("hasRole('APPROVER')")
    @PostMapping
    public ResponseEntity<ApprovalResponseDto> approve(
            @RequestBody ApprovalRequestDto dto,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                approvalService.approve(dto, authentication.getName())
        );
    }
    @PreAuthorize("hasAnyRole('APPROVER','ADMIN') or @documentSecurity.isOwnerByDocumentId(#documentId, authentication.name)")
    @GetMapping("/{documentId}")
    public ResponseEntity<List<ApprovalResponseDto>> getByDoc(@PathVariable Long documentId) {
        return ResponseEntity.ok(approvalService.getByDocument(documentId));
    }

}
