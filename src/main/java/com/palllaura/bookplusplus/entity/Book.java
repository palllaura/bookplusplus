package com.palllaura.bookplusplus.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
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

}