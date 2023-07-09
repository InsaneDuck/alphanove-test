package dev.insaneduck.alphanovetest.entites;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "book",schema = "data")
@Data
@Builder
public class Book {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "isbn")
    private String isbn;
    @Column(name = "publisher")
    private String publisher;
    @Column(name = "publication_year")
    private Integer publicationYear;
    @Column(name = "genre")
    private String genre;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private Double price;
    @Column(name = "language")
    private String language;
    @Column(name = "page_count")
    private Integer pageCount;
    @Column(name = "cover_image")
    private String coverImage;
    @Column(name = "availability")
    private Boolean availability;
    @Column(name = "rating")
    private Double rating;
    @Column(name = "edition")
    private Integer edition;
    @Column(name = "series")
    private String series;

    public Book() {

    }

    public Book(Integer id, String title, String author, String isbn, String publisher, Integer publicationYear, String genre, String description, Double price, String language, Integer pageCount, String coverImage, Boolean availability, Double rating, Integer edition, String series) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.description = description;
        this.price = price;
        this.language = language;
        this.pageCount = pageCount;
        this.coverImage = coverImage;
        this.availability = availability;
        this.rating = rating;
        this.edition = edition;
        this.series = series;
    }
}