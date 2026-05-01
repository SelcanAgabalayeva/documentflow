package com.selcan.documentflow.controllers;

import com.selcan.documentflow.dtos.documents.DocumentCreateDto;
import com.selcan.documentflow.dtos.documents.DocumentResponseDto;
import com.selcan.documentflow.dtos.documents.DocumentSubmitMessage;
import com.selcan.documentflow.integration.gateway.WorkflowGateway;
import com.selcan.documentflow.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/upload")
    public ResponseEntity<DocumentResponseDto> upload(
            @ModelAttribute DocumentCreateDto dto,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return ResponseEntity.ok(documentService.upload(dto, email));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-documents")
    public ResponseEntity<List<DocumentResponseDto>> myDocuments(Authentication authentication) {
        return ResponseEntity.ok(documentService.getMyDocuments(authentication.getName()));
    }



}
