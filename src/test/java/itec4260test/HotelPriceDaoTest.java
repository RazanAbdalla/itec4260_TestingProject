package itec4260test;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HotelPriceDaoTest {

    private HotelPriceDao dao;
    private HotelPrice testHotelPrice=new HotelPrice("testname","testcity","testdate",100);
    String nme=testHotelPrice.getHotelName();
    String city=testHotelPrice.getCity();
    String date=testHotelPrice.getDate();
    double price=testHotelPrice.getPrice();

    @BeforeAll
    void setupAll() {
        Database.createTable();
        Database.createTable();
    }

    @BeforeEach
    void setup() {
        dao = new HotelPriceDao();
        dao.clear();
    }

    @AfterEach
    void tearDown() {
        dao.clear();
    }

    @Ignore
    @Disabled
    @Test
    public void testInsertAndRetrieve() {
        HotelPrice hp = new HotelPrice("The Plaza", "New York", "2025-11-01", 1200);
        dao.insert(hp);

        List<HotelPrice> list = dao.getLowestPrices("The Plaza", "New York");
        assertEquals(1, list.size());
        assertEquals(1200, list.get(0).getPrice());
    }

    @Ignore
    @Disabled
    @Test
    public void testClear() {
        dao.insert(new HotelPrice("Hotel A", "City A", "2025-11-02", 500));
        dao.clear();
        assertTrue(dao.getAll().isEmpty());
    }

    @Ignore
    @Disabled
    @Test
    public void testGetLowestPricesLimit() {
        for (int i = 0; i < 20; i++) {
            dao.insert(new HotelPrice("Hotel B", "City B", "2025-11-" + (i+1), 100+i));
        }
        List<HotelPrice> lowest = dao.getLowestPrices("Hotel B", "City B");
        assertEquals(10, lowest.size());
        for (int i = 0; i < lowest.size()-1; i++) {
            assertTrue(lowest.get(i).getPrice() <= lowest.get(i+1).getPrice());
        }
    }

    @Ignore
    @Disabled
    @Test
    public void priceIsPositive(){
        WebScraper webScraper=new WebScraper();
        double price= webScraper.getPrice("city","Hotel",LocalDate.now());
        assertTrue(price > 0);
    }

    @Test
    void testMockito() {
        WebScraper webScraper = mock(WebScraper.class);
        BookingHotelScraper bookingHotelScraper = mock(BookingHotelScraper.class);
        HotelPrice hotel = new HotelPrice(webScraper, bookingHotelScraper);
        hotel.setHotel("hotel name", "hotel city", "11/1", 500.0);
        when(webScraper.getPrice("hotel city", "hotel name", LocalDate.now()))
                .thenReturn(500.0);
        double price = webScraper.getPrice("hotel city", "hotel name", LocalDate.now());
        assertEquals(500.0, price, 0.0);
        verify(webScraper).getPrice("hotel city", "hotel name", LocalDate.now());
        Mockito.verifyNoMoreInteractions(webScraper);
    }

    @Test
    public void testMockito2() {
        WebScraper webScraper = mock(WebScraper.class);
        BookingHotelScraper bookingHotelScraper = mock(BookingHotelScraper.class);
        HotelPrice hotel = new HotelPrice(webScraper, bookingHotelScraper);
        hotel.setHotel("hotel", "city", "11/7", 1100.0);
        when(bookingHotelScraper.scrapePrice("hotel", "city", LocalDate.now()))
                .thenReturn(1100.0);
        double price = bookingHotelScraper.scrapePrice("hotel", "city", LocalDate.now());
        assertEquals(1100.0, price, 0.0);
        verify(bookingHotelScraper).scrapePrice("hotel", "city", LocalDate.now());
        Mockito.verifyNoMoreInteractions(bookingHotelScraper);
    }

    @Test
    public void testHotelInfo(){
        assertEquals(nme,testHotelPrice.getHotelName());
        Assert.assertEquals(city,testHotelPrice.getCity());
        Assert.assertEquals(date,testHotelPrice.getDate());
        Assert.assertEquals(price,testHotelPrice.getPrice(),0);
    }
}
