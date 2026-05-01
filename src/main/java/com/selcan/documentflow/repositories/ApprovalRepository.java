package com.selcan.documentflow.repositories;

import com.selcan.documentflow.entity.Approval;
import com.selcan.documentflow.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApprovalRepository extends JpaRepository<Approval, Long> {
    List<Approval> findByDocumentId(Long documentId);
    @Query("""
   select distinct a.document from Approval a
   where a.approver.email = :email
   and a.decision is null
""")
    List<Document> findPendingDocumentsForApprover(String email);
    Optional<Approval> findByDocument_IdAndApprover_Email(Long documentId, String email);


}
