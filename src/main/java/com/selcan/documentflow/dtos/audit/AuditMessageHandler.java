package com.selcan.documentflow.dtos.audit;

import com.selcan.documentflow.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuditMessageHandler {

    private final AuditService auditService;

    public void handle(String event, String user, Long docId) {
        auditService.log(event, user, docId);
    }
}
