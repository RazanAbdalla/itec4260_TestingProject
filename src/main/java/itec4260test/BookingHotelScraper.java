package itec4260test;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class BookingHotelScraper {

    private WebDriver driver;

    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public double scrapePrice(String hotelName, String city, LocalDate checkIn) {
        double price = 0.0;

        try {
            String query = hotelName + " " + city;
            String url = String.format(
                    "https://www.booking.com/searchresults.html?ss=%s&checkin=%s&checkout=%s&group_adults=2&no_rooms=1",
                    query.replace(" ", "+"),
                    checkIn,
                    checkIn.plusDays(1)
            );

            driver.get(url);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Wait for results to appear
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[data-testid='property-card']")));

            // Find all hotel cards
            List<WebElement> hotels = driver.findElements(By.cssSelector("div[data-testid='property-card']"));

            for (WebElement card : hotels) {
                // Find the hotel name in this card
                String name = "";
                try {
                    WebElement nameElement = card.findElement(By.cssSelector("div[data-testid='title']"));
                    name = nameElement.getText().trim();
                } catch (NoSuchElementException ignore) {}

                // Match the hotel by name
                if (name.toLowerCase().contains(hotelName.toLowerCase())) {
                    try {
                        // Find all price elements and pick the last one
                        List<WebElement> priceElements = card.findElements(
                                By.cssSelector("span[data-testid='price-and-discounted-price'], span[data-testid='price']")
                        );

                        if (!priceElements.isEmpty()) {
                            WebElement priceElement = priceElements.get(priceElements.size() - 1); // last price
                            String text = priceElement.getText().replaceAll("[^0-9]", "");
                            if (!text.isEmpty()) {
                                price = Double.parseDouble(text);
                                break;
                            }
                        }

                    } catch (NoSuchElementException ignore) {}
                }
            }

            // Fallback: if still not found, take the last price of the first card
            if (price == 0 && !hotels.isEmpty()) {
                try {
                    List<WebElement> fallbackPrices = hotels.get(0).findElements(
                            By.cssSelector("span[data-testid='price-and-discounted-price'], span[data-testid='price']")
                    );
                    if (!fallbackPrices.isEmpty()) {
                        WebElement last = fallbackPrices.get(fallbackPrices.size() - 1);
                        String text = last.getText().replaceAll("[^0-9]", "");
                        if (!text.isEmpty()) price = Double.parseDouble(text);
                    }
                } catch (NoSuchElementException ignore) {}
            }

        } catch (Exception e) {
            System.out.println("Error scraping " + hotelName + " in " + city + ": " + e.getMessage());
        }

        return price;
    }

    public void quitDriver() {
        if (driver != null) driver.quit();
    }
}
