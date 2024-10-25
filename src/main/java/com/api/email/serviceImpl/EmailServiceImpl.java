package com.api.email.serviceImpl;


import com.api.email.dtos.SendEmailDTO;
import com.api.email.services.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public ResponseEntity<String> sendEmail(SendEmailDTO data) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("noreply@gmail.com");
            helper.setSubject("Dev Bueno");
            helper.setTo(data.email());

            String template  = loadEmailTemplate();

            template = template.replace("#{nome}", data.nome());
            helper.setText(template, true);
            javaMailSender.send(message);
            return ResponseEntity.ok("Email enviado com sucesso!");
        } catch (MailException mailException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Falha ao enviar o e-mail: " + mailException.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao enviar e-mail: " + exception.getMessage());
        }
    }

    @Override
    public String loadEmailTemplate() throws IOException {
        ClassPathResource resource = new ClassPathResource("/templates/template-email.html");
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
}

