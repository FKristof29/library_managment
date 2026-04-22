package library.model;

public class Book {
    private final String isbn;
    private final String title;
    private final String author;
    private final String topic;
    private final String description;
    private boolean available;

    public Book(String isbn, String title, String author, String topic, String description) {
        this.isbn        = isbn;
        this.title       = title;
        this.author      = author;
        this.topic       = topic;
        this.description = description;
        this.available   = true;
    }

    public String getIsbn()        { return isbn; }
    public String getTitle()       { return title; }
    public String getAuthor()      { return author; }
    public String getTopic()       { return topic; }
    public String getDescription() { return description; }
    public boolean isAvailable()   { return available; }
    public void setAvailable(boolean v) { this.available = v; }

    @Override
    public String toString() {
        return String.format("[%s] \"%s\" - %s | Tema: %s | %s",
                isbn, title, author, topic, available ? "Elerheto" : "Kolcsonozve");
    }
}
