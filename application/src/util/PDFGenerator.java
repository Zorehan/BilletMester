package util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;

public class PDFGenerator {

    public void generateBarcodePDF(String filename, String pdfFilename)
    {
        try{
            String realFileName = filename + ".png";
            pdfFilename = pdfFilename + ".pdf";
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);
            PDImageXObject pdImage = PDImageXObject.createFromFile(realFileName, doc);
            PDPageContentStream contentStream = new PDPageContentStream(doc,page);
            contentStream.drawImage(pdImage, 100, 400);
            contentStream.close();
            doc.save(pdfFilename);
            doc.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
