package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.UserActivation;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IMailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class MailService implements IMailService {

    String activationMessage =
            "<p>Pozdrav,</p>"
                    + "<p>Aktivaciju naloga možete obaviti klikom na sledeći link:</p>";

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void sendMail(String recipientEmail, String token) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("fabcar.project@gmail.com", "FAB CAR");
        helper.setTo(recipientEmail);

        String subject = "Token za reset lozinke";

        String content = "<p>Pozdrav,</p>"
                + "<p>Zatražili ste da resetujete Vašu lozinku.</p>"
                + "<p>Ovo je token koji Vam je potreban za reset lozinke:</p>"
                + token + "<br>"
                + "<p>Ignorišite ovaj email ako se sećate Vaše lozinke, "
                + "ili ako niste napravili ovaj zahtev.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @Override
    public void sendActivationEmail(String recipientEmail, UserActivation activation) throws MessagingException,UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("fabcar.project@gmail.com", "FAB CAR");
        helper.setTo(recipientEmail);

        String subject = "Aktivacija naloga";

        String activationLink = "http://localhost:8084/api/passenger/activate/" + activation.getId();
        String body = this.activationMessage +
                "<a href='" + activationLink +"'>" + activationLink;

        helper.setSubject(subject);

        helper.setText(body, true);

        mailSender.send(message);
    }
}