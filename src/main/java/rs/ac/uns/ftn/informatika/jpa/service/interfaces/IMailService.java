package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.UserActivation;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface IMailService {
    void sendMail(String recipientEmail, String token) throws MessagingException, UnsupportedEncodingException;
    void sendActivationEmail(String recipientEmail, UserActivation activation) throws MessagingException,UnsupportedEncodingException;
}