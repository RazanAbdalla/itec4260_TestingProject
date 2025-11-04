package itec4260test;

import java.time.LocalDate;

public class WebScraper {

    private BookingHotelScraper scraper;

    public WebScraper() {
        scraper = new BookingHotelScraper();
        scraper.setup();
    }

    public double getPrice(String city, String hotelName, LocalDate date) {
        return scraper.scrapePrice(hotelName, city, date);
    }

    public void quit() {
        scraper.quitDriver();
    }
}
