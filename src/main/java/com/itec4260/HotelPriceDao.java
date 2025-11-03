package com.itec4260;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelPriceDao {

    public void insert(HotelPrice hp) {
        String sql = "INSERT INTO hotel_prices(hotel_name, city, date, price) VALUES (?,?,?,?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, hp.getHotelName());
            pstmt.setString(2, hp.getCity());
            pstmt.setString(3, hp.getDate());
            pstmt.setDouble(4, hp.getPrice());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<HotelPrice> getLowestPrices(String hotelName, String city) {
        String sql = "SELECT * FROM hotel_prices WHERE hotel_name=? AND city=? ORDER BY price ASC LIMIT 10";
        List<HotelPrice> list = new ArrayList<>();

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, hotelName);
            pstmt.setString(2, city);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                HotelPrice hp = new HotelPrice(
                        rs.getString("hotel_name"),
                        rs.getString("city"),
                        rs.getString("date"),
                        rs.getDouble("price")
                );
                hp.setId(rs.getInt("id"));
                list.add(hp);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<HotelPrice> getAll() {
        String sql = "SELECT * FROM hotel_prices";
        List<HotelPrice> list = new ArrayList<>();

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                HotelPrice hp = new HotelPrice(
                        rs.getString("hotel_name"),
                        rs.getString("city"),
                        rs.getString("date"),
                        rs.getDouble("price")
                );
                hp.setId(rs.getInt("id"));
                list.add(hp);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public void clear() {
        String sql = "DELETE FROM hotel_prices";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
