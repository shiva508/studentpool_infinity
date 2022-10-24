package com.pool.util;

import java.awt.Color;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

public class PdfUtill {

    public static Font headerFontBuilder(String fontType, Integer fontSize, Color color) {
        Font font = FontFactory.getFont(fontType,fontSize,color);
        return font;
    }
    
    public static Paragraph paragraphBuilder(Font font, String text, int alignment) {
        Paragraph para = new Paragraph(text, font);
        para.setAlignment(alignment);
        return para;
    }
    
    public static void addNewLine(Document document) throws DocumentException {
        document.add(Chunk.NEWLINE);
    }

    public static void addParagraph(Document document,Paragraph paragraph) throws DocumentException {
        document.add(paragraph);
    }
    
    public static Font fontBuilder(String fontType) {
        Font font = FontFactory.getFont(fontType);
        return font;
    }
    
    public static PdfPCell pdfCell(boolean addBackgroundColor,
                                   Color backgroundColor,
                                   boolean addHorizontalAlignment,
                                   int horizontalAlignment,
                                   boolean addBorderWidth,
                                   int borderWidth) {
        PdfPCell cell = new PdfPCell();
        if(addBackgroundColor) {
            cell.setBackgroundColor(backgroundColor); 
        }
        if(addHorizontalAlignment) {
            cell.setHorizontalAlignment(horizontalAlignment);
        }
        if(addBorderWidth) {
            cell.setBorderWidth(borderWidth);
        }
        
        return cell;
    }
    
    public static Phrase praseBuilder(String text, Font font) {
        Phrase phrase = new Phrase(text, font);
        return phrase;
    }
    
    public static PdfPTable tableBuilder(int NUMBER_OF_ROWS) {
        PdfPTable table = new PdfPTable(NUMBER_OF_ROWS);
        return table;
    }
    
}
