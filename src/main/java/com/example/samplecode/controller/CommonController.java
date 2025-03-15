package com.example.samplecode.controller;

import com.example.samplecode.dto.response.ResponseData;
import com.example.samplecode.dto.response.ResponseError;
//import com.example.samplecode.service.MailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/email")
@Slf4j
@Tag(name = "Common Controller", description = "User API")
public class CommonController {
//    private final MailService mailService;

//    @PostMapping("/send-mail")
//    public ResponseData<String> sendMail(@RequestParam String subject, @RequestParam String content, @RequestParam String recipients, @RequestParam MultipartFile[] files) {
//        log.info("Send mail to: {}", recipients);
//        try {
//            return new ResponseData<>(HttpStatus.ACCEPTED.value(), mailService.sendMail(subject, content, recipients, files), "Mail sent successfully");
//        } catch (Exception e) {
//            log.error("Send mail error: {}", e.getMessage());
//            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Send mail error");
//        }
//    }
}
