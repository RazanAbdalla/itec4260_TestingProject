package com.itec4260;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HotelPriceDaoTest {

    private HotelPriceDao dao;

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

    @Test
    void testInsertAndRetrieve() {
        HotelPrice hp = new HotelPrice("The Plaza", "New York", "2025-11-01", 1200);
        dao.insert(hp);

        List<HotelPrice> list = dao.getLowestPrices("The Plaza", "New York");
        assertEquals(1, list.size());
        assertEquals(1200, list.get(0).getPrice());
    }

    @Test
    void testClear() {
        dao.insert(new HotelPrice("Hotel A", "City A", "2025-11-02", 500));
        dao.clear();
        assertTrue(dao.getAll().isEmpty());
    }

    @Test
    void testGetLowestPricesLimit() {
        for (int i = 0; i < 20; i++) {
            dao.insert(new HotelPrice("Hotel B", "City B", "2025-11-" + (i+1), 100+i));
        }
        List<HotelPrice> lowest = dao.getLowestPrices("Hotel B", "City B");
        assertEquals(10, lowest.size());
        for (int i = 0; i < lowest.size()-1; i++) {
            assertTrue(lowest.get(i).getPrice() <= lowest.get(i+1).getPrice());
        }
    }
}
