package util;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Properties;

public class MailSender {

    public void sendEmail(String recipientEmail, String ticketNameSubject, String body, String pdfFilePath) {
        pdfFilePath = pdfFilePath + ".pdf";
        String senderEmail = "mesterbillet@gmail.com"; // Correct sender email
        String senderPassword = "hftb sywp xshg vrxx"; // Correct sender password
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
            System.out.println("Creating MimeMessage...");
            MimeMessage message = new MimeMessage(session);
            System.out.println("Setting From address...");
            message.setFrom(new InternetAddress(senderEmail));
            System.out.println("Adding recipient...");
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            System.out.println("Setting subject...");
            message.setSubject(ticketNameSubject);

                DataSource source = new FileDataSource(file.getAbsolutePath());
                BodyPart pdfAttachment = new MimeBodyPart();
                pdfAttachment.setDataHandler(new DataHandler(source));
                pdfAttachment.setFileName(file.getName());

                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(body);
                System.out.println("Preparing to send email...");

                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);
                multipart.addBodyPart(pdfAttachment);

                message.setContent(multipart);

                Transport.send(message);
                System.out.println("Email sent successfully");
            } else {
                System.out.println("File not found: " + pdfFilePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
