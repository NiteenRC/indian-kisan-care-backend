package com.nc.med.mail;

import com.nc.med.model.User;
import com.nc.med.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Optional;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${email.from.address}")
    private String fromAddress;

    @Value("${email.receiver.username}")
    private String username;

    @Value("${root.dir.path}")
    private String dirPath;

    @Autowired
    private UserRepository userRepository;

    private File getLatestFilefromDir(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }

        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
            if (lastModifiedFile.lastModified() < files[i].lastModified()) {
                lastModifiedFile = files[i];
            }
        }
        return lastModifiedFile;
    }

    @Scheduled(cron = "0 0/56 12 * * *")
    public void sendMailAttachment() throws MessagingException, IOException {
        File fileDirPath = getLatestFilefromDir(dirPath);
        Optional<User> userOptional = userRepository.findById(1L);
        if (userOptional.isPresent()) {
            sendMailMultipart(username, String.valueOf(InetAddress.getLocalHost()), userOptional.get().getUsername(), fileDirPath);
        }
    }

    public void sendMailMultipart(String toEmail, String subject, String message, File file) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(fromAddress);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(message);

        if (file != null) {
            helper.addAttachment(file.getName(), file);
        }
        javaMailSender.send(mimeMessage);
    }

    @PostConstruct
    @EventListener(ApplicationReadyEvent.class)
    public void createDir() {
        File theDir = new File(dirPath);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
    }
}
