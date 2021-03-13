package com.arun.banking.util;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.arun.banking.entities.AccountBalanceHistory;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class PDFUtil {
    public ByteArrayOutputStream convertTransactionHistoryToPDF(List<AccountBalanceHistory> abh) {
        // Building the PDF
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, byteStream);
        document.open();
        Font font = new Font();
        font.setSize(8);
        abh.forEach((transaction) -> {
            Paragraph p = new Paragraph(transaction.toString(), font);
            document.add(p);
            p = new Paragraph("---------");
            document.add(p);
        });
        document.close();

        return byteStream;
    }
}
