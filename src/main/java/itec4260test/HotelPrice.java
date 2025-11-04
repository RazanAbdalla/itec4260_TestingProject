package itec4260test;

public class HotelPrice {
    private int id;
    private String hotelName;
    private String city;
    private String date;
    private double price;

    public HotelPrice(String hotelName, String city, String date, double price) {
        this.hotelName = hotelName;
        this.city = city;
        this.date = date;
        this.price = price;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
