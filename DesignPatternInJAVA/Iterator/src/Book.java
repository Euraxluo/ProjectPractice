public class Book {
    private String ISBN;
    private String name;
    private double price;

    public Book(String ISBN, String name, double price) {
        this.ISBN = ISBN;
        this.name = name;
        this.price = price;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ISBN=" + ISBN + ",name=" + name + ",price=" + price;
    }
}
