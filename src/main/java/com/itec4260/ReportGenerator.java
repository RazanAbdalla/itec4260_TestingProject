package com.itec4260;

import org.apache.poi.xwpf.usermodel.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ReportGenerator {

    public void generateReport(List<HotelPrice> list, String hotelName, String city) {
        XWPFDocument doc = new XWPFDocument();

        XWPFParagraph p = doc.createParagraph();
        XWPFRun run = p.createRun();
        run.setText("Top 10 Lowest Prices for " + hotelName + " in " + city);

        XWPFTable table = doc.createTable();
        XWPFTableRow header = table.getRow(0);
        header.getCell(0).setText("Date");
        header.addNewTableCell().setText("Price");

        for (HotelPrice hp : list) {
            XWPFTableRow row = table.createRow();
            row.getCell(0).setText(hp.getDate());
            row.getCell(1).setText(String.valueOf(hp.getPrice()));
        }

        try (FileOutputStream out = new FileOutputStream("Report_" + city + "_" + hotelName + ".docx")) {
            doc.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
