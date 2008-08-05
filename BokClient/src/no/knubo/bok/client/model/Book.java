package no.knubo.bok.client.model;

public class Book {
    String title;
    int authorId;
    int publisherId;
    String author;
    String publisher;
    int writtenYear;

    public String getTitle() {
        return title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setWrittenYear(int writtenYear) {
        this.writtenYear = writtenYear;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getWrittenYear() {
        return writtenYear;
    }
}
