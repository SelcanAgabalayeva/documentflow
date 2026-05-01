package com.selcan.documentflow.entity;
import com.selcan.documentflow.enums.ApprovalDecision;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "approvals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Approval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @Enumerated(EnumType.STRING)
    private ApprovalDecision decision;

    private LocalDateTime decisionAt;

    @ManyToOne
    private Document document;

    @ManyToOne
    private User approver;
}
