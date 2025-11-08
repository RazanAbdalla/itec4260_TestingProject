package itec4260test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportGenerator {

    private static final String FILE_NAME = "All_Hotels_Report.csv";

    public void generateReport(List<HotelPrice> list, String hotelName, String city) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) { // 'true' for append mode

            // Add a title for this hotel/city
            writer.append("\n===============================\n");
            writer.append("Top 10 Lowest Prices for ").append(hotelName).append(" in ").append(city).append("\n");
            writer.append("===============================\n");

            // Write header for this section
            writer.append("Date,Price\n");

            // Write each hotel price
            for (HotelPrice hp : list) {
                writer.append(hp.getDate()).append(",").append(String.valueOf(hp.getPrice())).append("\n");
            }

            // Add a blank line for separation
            writer.append("\n");

            System.out.println("Added report for " + hotelName + " in " + city);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
