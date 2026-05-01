package com.selcan.documentflow.integration.service;


import com.selcan.documentflow.entity.Document;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailNotificationService {

    private final JavaMailSender mailSender;

    public void sendApprovalRequest(Document document, String approverEmail) {

        String approveInstruction = "POST /api/approvals with decision APPROVED for documentId=" + document.getId();
        String rejectInstruction = "POST /api/approvals with decision REJECTED for documentId=" + document.getId();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(approverEmail);
        message.setSubject("Document Approval Request");
        message.setText(
                "A new document requires your approval.\n\n" +
                        "Title: " + document.getTitle() + "\n" +
                        "Description: " + document.getDescription() + "\n\n" +
                        approveInstruction + "\n" +
                        rejectInstruction
        );

        mailSender.send(message);
    }
}
