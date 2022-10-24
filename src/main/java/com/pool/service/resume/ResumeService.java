package com.pool.service.resume;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import static com.pool.util.PdfConstants.*;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.pool.util.PdfUtill;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ResumeService {
    
    @Autowired
    private TemplateEngine templateEngine;
    
    

    public ByteArrayInputStream generatePdf() {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfWriter.getInstance(document, out);
            document.open();
     
            Font headerFont = PdfUtill.headerFontBuilder(FONT_TIMES_BOLD,FONT_SIZE_14,COLOR_GREEN);
            String inputHeaderText="Shiva Dasari";
            
            Paragraph para = PdfUtill.paragraphBuilder(headerFont,inputHeaderText,ALIGN_LEFT);
            PdfUtill.addParagraph(document,para);
            Paragraph emailParagraph = PdfUtill.paragraphBuilder(headerFont,"dasarishiva1@gmail.com",ALIGN_LEFT);
            PdfUtill.addParagraph(document,emailParagraph);
            PdfUtill.addNewLine(document);
            
            
            int NUMBER_OF_ROWS=3;
            PdfPTable table = PdfUtill.tableBuilder(NUMBER_OF_ROWS);
// Add PDF Table Header ->
            Stream.of("ID", "First Name", "Last Name")
                    .forEach(headerTitle -> {
                        PdfPCell header = PdfUtill.pdfCell(false,COLOR_LIGHT_GRAY,
                                                           true,ALIGN_CENTER,
                                                           false,BORDER_WIDTH_2);
                        Font headFont = PdfUtill.fontBuilder(FONT_HELVETICA_BOLD);
                        
                        Phrase prase = PdfUtill.praseBuilder(headerTitle,headFont);
                        header.setPhrase(prase);
                        table.addCell(header);
                    });

            document.add(table);

            document.close();
        } catch (DocumentException e) {
            log.error(e.toString());
        }
        

        return new ByteArrayInputStream(out.toByteArray());

    }

    
    public ByteArrayInputStream generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName) {
        Context context = new Context();
        context.setVariables(data);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String htmlContent = templateEngine.process(templateName, context);
        try {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(out, false);
            renderer.finishPDF();
        }catch (com.lowagie.text.DocumentException e) {
            log.error(e.getMessage(), e);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
    
    public ByteArrayInputStream generateOminiPdfFile(String templateName,String pdfFileName) {
        Context context = new Context();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String htmlContent = templateEngine.process(templateName, context);
        try {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(out, false);
            renderer.finishPDF();
        }catch (com.lowagie.text.DocumentException e) {
            log.error(e.getMessage(), e);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
    

    
}
