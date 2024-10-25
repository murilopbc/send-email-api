package com.api.email.controller;

import com.api.email.services.EmailService;
import com.api.email.dtos.SendEmailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emails")
@RequiredArgsConstructor
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping
    public ResponseEntity<String> sendEmail(@RequestBody SendEmailDTO data) {
        return emailService.sendEmail(data);
    }
}

