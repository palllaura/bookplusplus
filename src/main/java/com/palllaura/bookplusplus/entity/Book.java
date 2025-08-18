package com.palllaura.bookplusplus.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "book")
public class Book {
    /**
     * Unique ID of each book.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Book title.
     */
    @Column(name = "title")
    private String title;
    /**
     * Number of pages.
     */
    @Column(name = "pages")
    private int pages;
    /**
     * Book width in millimeters.
     */
    @Column(name = "width")
    private int bookWidthInMm;
    /**
     * Book height in millimeters.
     */
    @Column(name = "height")
    private int bookHeightInMm;
    /**
     * Color code of book.
     */
    @Column(name = "color")
    private String color;
    /**
     * X coordinate of book position.
     */
    @Column(name = "x")
    private int xPosition;
    /**
     * Y coordinate of book position.
     */
    @Column(name = "y")
    private int yPosition;

    /**
     * No-args constructor.
     */
    public Book() {
    }

    /**
     * Book constructor.
     * @param title Book title as string.
     * @param pages Number of pages.
     * @param bookWidthInMm Book width as int.
     * @param bookHeightInMm Book height as int.
     * @param color Book color as string.
     * @param xPosition Book x position as int.
     * @param yPosition Book y position as int.
     */
    public Book(String title, int pages, int bookWidthInMm, int bookHeightInMm, String color, int xPosition, int yPosition) {
        this.title = title;
        this.pages = pages;
        this.bookWidthInMm = bookWidthInMm;
        this.bookHeightInMm = bookHeightInMm;
        this.color = color;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
}