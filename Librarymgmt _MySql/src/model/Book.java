package model;

public class Book {
    private String bookId;
    private String title;
    private String author;
    private String publisher;
    private int quantity;

    public Book(String bookId, String title, String author, String publisher, int quantity) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.quantity = quantity;
    }

    // Getters and setters
    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getPublisher() { return publisher; }
    public int getQuantity() { return quantity; }

    public void setBookId(String bookId) { this.bookId = bookId; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
