package util;

import BE.Events;
import BE.Users.User;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFGenerator {


    public void generateBarcodePDF(String filename, String pdfFilename, Events event, User user) {
        try {
            String eventName = event.getEventName();
            String eventDescription = event.getEventNotes();
            String userName = user.getFullName();
            String eventLocation = event.getEventLocation();
            String realFileName = filename + ".png";
            pdfFilename = pdfFilename + ".pdf";
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(doc, page);


            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 22);
            contentStream.newLineAtOffset(100, 750);
            contentStream.showText("BILLETMESTER");
            contentStream.endText();


            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 16);
            contentStream.newLineAtOffset(100, 630);
            contentStream.showText(eventName);
            contentStream.endText();

            
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            float startXEventDesc = 100;
            float startYEventDesc = 600;
            drawWrappedText(contentStream, eventDescription, startXEventDesc, startYEventDesc, 12, page.getMediaBox().getWidth() - 100);

            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            float userNameX = page.getMediaBox().getWidth() - 100 - new PDType1Font(Standard14Fonts.FontName.HELVETICA).getStringWidth(userName) / 1000 * 12;
            float userNameY = 750;
            contentStream.newLineAtOffset(userNameX, userNameY);
            contentStream.showText("Ticket for: " + userName);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            float eventLocationX = page.getMediaBox().getWidth() - 100 - new PDType1Font(Standard14Fonts.FontName.HELVETICA).getStringWidth(eventLocation) / 1000 * 12;
            float eventLocationY = 730;
            contentStream.newLineAtOffset(eventLocationX, eventLocationY);
            contentStream.showText(eventLocation);
            contentStream.endText();


            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
            float textWidth = new PDType1Font(Standard14Fonts.FontName.HELVETICA).getStringWidth("SCAN HERE") / 1000 * 16;
            float textX = (PDRectangle.A4.getWidth() - textWidth) / 2;
            float textY = 150;
            contentStream.newLineAtOffset(textX, textY);
            contentStream.showText("SCAN HERE");
            contentStream.endText();


            PDImageXObject pdImage = PDImageXObject.createFromFile(realFileName, doc);


            float scale = 0.5f;
            float imageWidth = pdImage.getWidth() * scale;
            float imageHeight = pdImage.getHeight() * scale;
            float startX = (PDRectangle.A4.getWidth() - imageWidth) / 2;
            float startY = 50;

            contentStream.drawImage(pdImage, startX, startY, imageWidth, imageHeight);

            contentStream.close();
            doc.save(pdfFilename);
            doc.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void drawWrappedText(PDPageContentStream contentStream, String text, float x, float y, int fontSize, float maxWidth) throws IOException {
        PDType1Font fontIUse = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
        contentStream.setFont(fontIUse, fontSize);
        List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();
        float currentLineWidth = 0;

        for (String word : text.split("\\s+")) {
            float wordWidth = fontIUse.getStringWidth(word) / 1000 * fontSize;
            if (currentLineWidth + wordWidth > maxWidth) {
                lines.add(currentLine.toString().trim());
                currentLine = new StringBuilder();
                currentLineWidth = 0;
            }
            currentLine.append(word).append(" ");
            currentLineWidth += wordWidth + fontIUse.getFontDescriptor().getAverageWidth() / 1000 * fontSize;
        }
        lines.add(currentLine.toString().trim());


        for (String line : lines) {
            contentStream.beginText();
            contentStream.newLineAtOffset(x, y);
            contentStream.showText(line);
            y -= fontSize;
            contentStream.endText();
        }

    }
    
}
