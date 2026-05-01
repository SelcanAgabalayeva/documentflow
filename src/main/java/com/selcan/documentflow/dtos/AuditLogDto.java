package com.selcan.documentflow.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AuditLogDto {

    private Long id;
    private String action;
    private String performedBy;
    private Long documentId;
    private LocalDateTime timestamp;
}
