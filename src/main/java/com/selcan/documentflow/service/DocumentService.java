package com.selcan.documentflow.service;

import com.selcan.documentflow.dtos.documents.DocumentCreateDto;
import com.selcan.documentflow.dtos.documents.DocumentResponseDto;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface DocumentService {
    DocumentResponseDto upload(DocumentCreateDto dto, String username);

    void delete(Long id);

    void approve(Long documentId, String approverEmail);

    void reject(Long documentId, String approverEmail);

    @Nullable List<DocumentResponseDto> getMyDocuments(String name);

    @Nullable List<DocumentResponseDto> getPendingApprovals(String name);

}
