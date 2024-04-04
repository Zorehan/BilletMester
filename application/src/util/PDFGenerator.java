package util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


import java.io.IOException;

public class PDFGenerator {

    public static void main(String args[])
    {
        generateBarcodePDF("EstorStorhaha", "Baaanger", "test 2", "lidtmeretestergodnok, Ejasdlkhsaflækajsdlaskjdn.,xcvnaslædkfdjsdlfkjalskdfjadlkjasdlkajdalskdfjalksdjsldgkjsdflknsdvlksdnfladkfnlsadknfgalkdfnsalkdnasldknasdlknasdlkasndaslkdn");
    }

    public static void generateBarcodePDF(String filename, String pdfFilename, String eventName, String eventDescription) {
        try {
            String realFileName = filename + ".png";
            pdfFilename = pdfFilename + ".pdf";
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(doc, page);

            // Add Title
            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 22);
            contentStream.newLineAtOffset(100, 750);
            contentStream.showText("BILLETMESTER");
            contentStream.endText();

            // Add Event Name
            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 16);
            contentStream.newLineAtOffset(100, 630);
            contentStream.showText(eventName);
            contentStream.endText();

            // Add Event Description
            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            contentStream.newLineAtOffset(100, 600);
            contentStream.showText(eventDescription);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16); // Larger and bolder text
            float textWidth = new PDType1Font(Standard14Fonts.FontName.HELVETICA).getStringWidth("SCAN HERE") / 1000 * 16;
            float textX = (PDRectangle.A4.getWidth() - textWidth) / 2; // Center the text
            float textY = 150; // Position above the barcode
            contentStream.newLineAtOffset(textX, textY);
            contentStream.showText("SCAN HERE");
            contentStream.endText();

            // Adding the Barcode Image
            PDImageXObject pdImage = PDImageXObject.createFromFile(realFileName, doc);

            // Calculate to center the image
            float scale = 0.5f; // Adjust scale to fit the page or as needed
            float imageWidth = pdImage.getWidth() * scale;
            float imageHeight = pdImage.getHeight() * scale;
            float startX = (PDRectangle.A4.getWidth() - imageWidth) / 2;
            float startY = 50; // Position at the bottom

            contentStream.drawImage(pdImage, startX, startY, imageWidth, imageHeight);

            contentStream.close();
            doc.save(pdfFilename);
            doc.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
