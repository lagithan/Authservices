package org.example.authservice.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.authservice.config.AppConfig;
import org.example.authservice.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// Correct imports for JavaMail
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class EmailUtil {

    @Autowired
    private AppConfig appConfig;

    // Email template - should be loaded from a file or configuration
    @Autowired
    private EmailTemplates emailTemplates;


    public void sendVerificationEmail(String recipientEmail, String recipientName, String token)
            throws MessagingException {

       String redirectUrl = appConfig.getVerificationRedirect()+"?token="+token;
        // Personalize the email template
        String personalizedEmail = emailTemplates.getVerificationTemplate()
                .replace("{{User}}", recipientName)
                .replace("{{redirecturl}}", redirectUrl);

        // Set up mail server properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", appConfig.getSmtpHost());
        props.put("mail.smtp.port", appConfig.getSmtpPort());

        // Create a mail session with authenticator
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        appConfig.getSmtpUsername(),
                        appConfig.getSmtpPassword()
                );
            }
        });

        // Create a message
        Message message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress("teamoffoura@offoura.com", "Offoura"));
        } catch (UnsupportedEncodingException e) {
            message.setFrom(new InternetAddress("teamoffoura@offoura.com"));
        }

        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject("Verify your email");

        // Set the HTML content
        message.setContent(personalizedEmail, "text/html; charset=utf-8");

        // Send the message
        try{
            Transport.send(message);
        }
        catch (Exception e){
            throw new BadRequestException("Failed to send verification email");
        }
    }
}