package util;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Properties;

public class MailSender {

    public void sendEmail(String recipientEmail, String ticketNameSubject, String pdfFilePath) {

        String body = "Thank you for purchasing your ticket from BilletMester!";
        pdfFilePath = pdfFilePath + ".pdf";
        String senderEmail = "mesterbillet@gmail.com";
        String senderPassword = "hftb sywp xshg vrxx";
        String smtpHost = "smtp.gmail.com";
        int smtpPort = 465;

        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.user", senderEmail);
        properties.put("mail.smtp.pass", senderPassword);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.debug", "true");
        properties.put("mail.smtp.ssl.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            File file = new File(pdfFilePath);
            if(file.exists())
            {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject(ticketNameSubject);

                DataSource source = new FileDataSource(file.getAbsolutePath());
                BodyPart pdfAttachment = new MimeBodyPart();
                pdfAttachment.setDataHandler(new DataHandler(source));
                pdfAttachment.setFileName(file.getName());

                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(body);

                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);
                multipart.addBodyPart(pdfAttachment);

                message.setContent(multipart);

                Transport.send(message);
            } else {
                System.out.println("File not found: " + pdfFilePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
