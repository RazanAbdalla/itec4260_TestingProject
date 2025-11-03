package com.itec4260;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class BookingHotelScraper {

    private WebDriver driver;
    private boolean testMode = true;

    public void setup() {
        if (testMode) return;

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-blink-features=AutomationControlled");

        driver = new ChromeDriver(options);
    }

    public double scrapePrice(String hotelName, String city, LocalDate checkIn) {
        if (testMode) {

            Random random = new Random();
            return 100 + random.nextInt(300); // random price between 100–400
        }

        double price = 0.0;
        try {
            String url = String.format(
                    "https://www.booking.com/searchresults.html?ss=%s&checkin=%s&checkout=%s&group_adults=2&no_rooms=1",
                    hotelName.replace(" ", "+"),
                    checkIn,
                    checkIn.plusDays(1)
            );
            driver.get(url);
            Thread.sleep(3000); // wait for page to load

            List<WebElement> prices = driver.findElements(By.cssSelector("[data-testid='price-and-discounted-price']"));
            if (!prices.isEmpty()) {
                String text = prices.get(0).getText().replaceAll("[^0-9.]", "");
                if (!text.isEmpty()) price = Double.parseDouble(text);
            }

        } catch (Exception e) {
            // Silent fail for faster testing
        }
        return price;
    }

    public void quitDriver() {
        if (!testMode && driver != null) driver.quit();
    }
}
