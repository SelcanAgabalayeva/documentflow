package com.selcan.documentflow.service.impls;

import com.selcan.documentflow.dtos.AuditLogDto;
import com.selcan.documentflow.entity.AuditLog;
import com.selcan.documentflow.entity.User;
import com.selcan.documentflow.repositories.AuditLogRepository;
import com.selcan.documentflow.repositories.UserRepository;
import com.selcan.documentflow.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;

    @Override
    public void log(String action, String performedBy, Long documentId) {

        AuditLog log = new AuditLog();
        log.setAction(action);

        User user = null;
        if (performedBy != null) {
            user = userRepository.findByEmail(performedBy).orElse(null);
        }

        log.setPerformedBy(user);
        log.setDocumentId(documentId);
        log.setTimestamp(LocalDateTime.now());

        auditLogRepository.save(log);
    }

    @Override
    public List<AuditLogDto> getByDocument(Long documentId) {
        return auditLogRepository.findByDocumentId(documentId)
                .stream()
                .map(this::map)
                .toList();
    }

    private AuditLogDto map(AuditLog log) {
        return AuditLogDto.builder()
                .id(log.getId())
                .action(log.getAction())
                .performedBy(log.getPerformedBy() != null ? log.getPerformedBy().getEmail() : "SYSTEM")
                .documentId(log.getDocumentId())
                .timestamp(log.getTimestamp())
                .build();
    }
}
