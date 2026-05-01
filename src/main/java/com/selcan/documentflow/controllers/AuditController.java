package com.selcan.documentflow.controllers;

import com.selcan.documentflow.dtos.AuditLogDto;
import com.selcan.documentflow.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping("/{documentId}")
    public ResponseEntity<List<AuditLogDto>> getLogs(@PathVariable Long documentId) {
        return ResponseEntity.ok(auditService.getByDocument(documentId));
    }
}
