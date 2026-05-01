package com.selcan.documentflow.dtos.documents;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class DocumentResponseDto {
    private Long id;
    private String title;
    private String description;
    private String filePath;
    private String status;
    private LocalDateTime createdAt;
    private String createdBy;
}
