package com.selcan.documentflow.service;

import com.selcan.documentflow.dtos.AuditLogDto;

import java.util.List;

public interface AuditService {

    void log(String action, String performedBy, Long documentId);

    List<AuditLogDto> getByDocument(Long documentId);
}
