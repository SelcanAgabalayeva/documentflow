package com.selcan.documentflow.dtos.documents;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentUpdateStatusDto {

    @NotNull
    private Long documentId;
    @NotNull
    private String status;
}
