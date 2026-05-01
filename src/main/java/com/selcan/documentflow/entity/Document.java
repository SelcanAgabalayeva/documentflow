package com.selcan.documentflow.entity;

import com.selcan.documentflow.enums.DocumentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String filePath;
    @Enumerated(EnumType.STRING)
    private DocumentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToOne
    private User createdBy;
    @OneToMany(mappedBy = "document")
    private List<Approval> approvals;
}
