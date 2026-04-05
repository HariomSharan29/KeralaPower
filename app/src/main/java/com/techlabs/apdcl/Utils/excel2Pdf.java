package com.techlabs.apdcl.Utils;

import android.util.Log;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class excel2Pdf {

    public static void convertExcelToPdf(String inputPath, String outputPath) throws IOException {
        try {
            Workbook workbook;
            FileInputStream fis = new FileInputStream(inputPath);

            // Detect the Excel file format
            if (inputPath.endsWith(".xls")) {
                // For Excel 97-2003 format
                workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
            } else {
                // For Excel 2007+ format
                workbook = new XSSFWorkbook(fis);
            }

            // Create PDF document
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(outputPath)));
            // Iterate over sheets and write them to PDF
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                // Get the sheet
                org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(i);
                // Write the sheet to PDF
                new SheetToPdfConverter().writeSheetToPdf(sheet, pdfDoc);
            }
            // Close workbook and PDF
            workbook.close();
            pdfDoc.close();
            Log.d("Conversion", "Excel converted to PDF successfully");
        } catch (IOException e) {
            Log.e("Conversion", "Error converting Excel to PDF", e);
        }

    }

}
