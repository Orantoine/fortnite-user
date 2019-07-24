package fr.Orantoine.fortniteuser.classes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailHelper {

    @Autowired
    private JavaMailSender javaMailSender;

    public MailHelper() {
    }

    public void sendEmail(String destination) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(destination);

        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");

        javaMailSender.send(msg);
    }
}
