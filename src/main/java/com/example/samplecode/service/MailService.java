package com.example.samplecode.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine stringTemEngine;

    @Value("${spring.mail.from}")
    private String emailFrom;


    public String sendMail(String subject, String content, String recipients, MultipartFile[] files) throws MessagingException, UnsupportedEncodingException {
        log.info("Send mail to: {}", recipients);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(emailFrom, "Admin");

        if (recipients.contains(",")) {
            helper.setTo(InternetAddress.parse(recipients));
        } else {
            helper.setTo(recipients);
        }

        if (files != null) {
            for (MultipartFile file : files) {
                helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file);
            }
        }

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
        log.info("Mail sent successfully to: {}", recipients);

        // send mail
        return "Mail sent successfully";
    }

    public void sendConfirmationEmailLink(String email, Long userId, String secretCode) throws MessagingException, UnsupportedEncodingException {
        log.info("Send confirmation email link to: {}", email);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Context context = new Context();
        String linkConfirm = String.format("http://localhost:8888/api/v1/user/confirm/%s?secretCode=%s", userId, secretCode);

        Map<String, Object> properties = new HashMap<>();
        properties.put("linkConfirm", linkConfirm);
        context.setVariables(properties);
        helper.setFrom(emailFrom, "Admin");
        helper.setTo(email);
        helper.setSubject("Confirm your email address");

        String html = stringTemEngine.process("confirm-email.html", context);
        helper.setText(html, true);

        mailSender.send(message);
        log.info("Confirmation email link sent successfully to: {}", email);
    }
}
