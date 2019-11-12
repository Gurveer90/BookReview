package ui;

public class Reviewer {
    private String name;
    private String review;
    private Book book;

    public Reviewer(String name, String review, Book book) {
        this.name = name;
        this.review = review;
        this.book = book;
    }

    public String getName() {
        return name;
    }

    public String getReview() {
        return review;
    }

    public Book getBook() { return book;}
}
