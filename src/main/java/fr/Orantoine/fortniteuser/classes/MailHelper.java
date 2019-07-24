package fr.Orantoine.fortniteuser.classes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class MailHelper {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String destination) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(destination);

        msg.setSubject("Changement du mot de passe");
        msg.setText("Bonjour, vous avez demandé un renvouellement de mot de passe. Veuillez-suivre ce lien http://camarche.fr/");

        javaMailSender.send(msg);
    }
}
