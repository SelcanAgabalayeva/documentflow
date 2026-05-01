package com.selcan.documentflow.dtos.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentSubmitMessage {
    private Long documentId;
    private String submitterEmail;


}
