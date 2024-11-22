package com.example.Mini_Project1.utils;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.example.Mini_Project1.exception.ExceptionCode;
import com.example.Mini_Project1.exception.UserException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class MailUtils {
	private String[] characters = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c", "d", "e", "f", "g",
            "h", "i", "j", "k", "l", "m", "n", "o", "v", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };

    public String createToken() {
        String s = "";
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            int randomNumber = random.nextInt(characters.length - 1 - 0 + 1) + 0;
            s = s + characters[randomNumber];
        }
        return s;
    }
    
    @Autowired
    private JavaMailSender javaMailSender; // use an object of the lib of mail in maven.
    
    @Value("${spring.mail.username}")
    private String sender; // get sender from file application.properties

    private String recipient;
    private String msgBody;
    private String subject;
    
 // send HTML email
    public void sendHtmlEmail() {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(sender);
            helper.setTo(this.recipient);
            helper.setSubject(this.subject);
            helper.setText(this.msgBody, true); // 'true' indicates that this is an HTML email.

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new UserException(ExceptionCode.SendEmailFail);
        }
    }
    
    public String createAccountBody(String token) {
        return "<html>" +
                "<head>" +
                "<!-- Fonts -->" +
                "<link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">" +
                "<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>" +
                "<link href=\"https://fonts.googleapis.com/css2?family=Itim&display=swap\" rel=\"stylesheet\">" +
                "<link href=\"https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&family=Itim&display=swap\" rel=\"stylesheet\">"
                +
                "<style>" +
                "body p {" +
                "font-family: \"Inter\", sans-serif;" +
                "font-optical-sizing: auto;" +
                "font-style: normal;" +
                "}" +
                "* {" +
                "margin: 0;" +
                "padding: 0;" +
                "box-sizing: border-box;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div style=\"padding: 10px 10px; box-shadow: 2px 2px 12px gray; width: fit-content;\">" +
                "<p style=\"font-weight: 600; font-style: italic;\">HELLO YOU, THIS EMAIL IS FROM THE INTERNATIONAL ARTICLE MANAGEMENT SYSTEM.</p>"
                +
                "<br/>" +
                "<p style=\"color: black; width: 400px;\">This is token that we created for you. You can use it to reset your password! Please don not share it to anyone.</p>"
                +
                "<a href=\"https://www.facebook.com/yeu.con.90663894\">My website.</a>"
                +
                "<br/><br/>" +
                "<div>" +
                "<ul style=\"list-style-type: disc; padding-left: 20px;\">" +
                "<li style=\"display: flex; align-items: center; margin: 0;\">" +
//                "<span style=\"margin-right: 5px;\">•</span>" +
//                "<p style=\"margin: 0;\">Tài khoản: </p>" +
//                "<p style=\"margin-left: 5px; font-weight: 600;\">" + mssv + "</p>" +
//                "</li>" +
//                "<li style=\"display: flex; align-items: center; margin: 0;\">" +
                "<span style=\"margin-right: 5px;\">•</span>" +
                "<p style=\"margin: 0;\">Your token: </p>" +
                "<p style=\"margin-left: 5px; font-weight: 600;\">" + token + "</p>" +
                "</li>" +
                "</ul>" +
                "</div>" +
                "<br/>" +
                "<p style=\"font-style: italic; color: red;\">This email is created automatically - pleased do not reply! Thank you.</p>"
                +
                "</div>" +
                "</body>" +
                "</html>";
    }
    
}
