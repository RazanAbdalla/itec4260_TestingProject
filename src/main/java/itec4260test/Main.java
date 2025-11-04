package itec4260test;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Database.createTable();

        HotelPriceDao dao = new HotelPriceDao();
        dao.clear();

        WebScraper scraper = new WebScraper();
        ReportGenerator report = new ReportGenerator();

        String[] hotels = {"Four Seasons", "Ritz-Carlton", "Park Hyatt", "St. Regis Hotel", "Waldorf Astoria Hotel"};
        String[] cities = {"Las Vegas", "New York", "Miami", "Paris", "Los Angeles"};

        LocalDate start = LocalDate.of(2025, 11, 15);
        LocalDate end = LocalDate.of(2026, 5, 1);

        for (String city : cities) {
            for (String hotel : hotels) {
                for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
                    double price = scraper.getPrice(city, hotel, date);
                    if (price > 0) {
                        dao.insert(new HotelPrice(hotel, city, date.toString(), price));
                    }
                }

                List<HotelPrice> lowest = dao.getLowestPrices(hotel, city);
                if (!lowest.isEmpty()) report.generateReport(lowest, hotel, city);
            }
        }

        scraper.quit();
    }
}
