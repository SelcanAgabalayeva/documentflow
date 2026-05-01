package com.selcan.documentflow.repositories;

import com.selcan.documentflow.entity.Document;
import com.selcan.documentflow.enums.DocumentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByCreatedByEmail(String name);



}
