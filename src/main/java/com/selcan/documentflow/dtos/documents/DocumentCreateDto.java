package com.selcan.documentflow.dtos.documents;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class DocumentCreateDto {
    @NotBlank(message = "Title cannot be empty")
    private String title;
    private String description;
    private MultipartFile file;
    private Long approverId;
}
