package com.techlabs.apdcl.Utils;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelToPdfConverter {

    public static void convertExcelToPdf(File excelFile, File pdfFile) throws IOException {
        FileInputStream excelInput = new FileInputStream(excelFile);
        Workbook workbook = new XSSFWorkbook(excelInput);
        Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet is present

        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(pdfFile));
        Document document = new Document(pdfDocument);
        Table table = new Table(sheet.getRow(0).getPhysicalNumberOfCells());

        for (Row row : sheet) {
            for (Cell cell : row) {
                table.addCell(cell.toString());
            }
        }

        document.add(table);
        document.close();
        workbook.close();
        excelInput.close();
    }

}
