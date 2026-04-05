package com.techlabs.apdcl.Utils;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import java.io.IOException;

public class SheetToPdfConverter {
    public void writeSheetToPdf(Sheet sheet, PdfDocument pdfDoc) throws IOException {
        Document document = new Document(pdfDoc);

        Table table = new Table(sheet.getRow(0).getLastCellNum());
//        table.setWidthPercent(100);

        for (Row row : sheet) {
            for (Cell cell : row) {
                table.addCell(cell.toString());
            }
        }

        document.add(table);
        document.close();
    }
}
