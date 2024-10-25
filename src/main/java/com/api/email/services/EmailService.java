package com.api.email.services;

import com.api.email.dtos.SendEmailDTO;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface EmailService {

    ResponseEntity<String> sendEmail(SendEmailDTO data);
    String loadEmailTemplate() throws IOException;
}
