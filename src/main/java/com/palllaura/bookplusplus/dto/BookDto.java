package com.palllaura.bookplusplus.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {
    /**
     * ID of book.
     */
    private Long id;
    /**
     * Title of book.
     */
    private String title;
    /**
     * Number of pages.
     */
    private int pages;
    /**
     * Book height in millimeters.
     */
    private int height;
    /**
     * Font size for display.
     */
    private int fontsize;
    /**
     * Color code of book.
     */
    private String color;
    /**
     * Color code of book title.
     */
    private String fontcolor;

    /**
     * Constructor for book editing.
     * @param id id of book.
     * @param title title of book.
     * @param pages number of pages.
     * @param height height of book.
     * @param fontsize font size for display.
     * @param color book color for display.
     * @param fontcolor font color for display.
     */
    public BookDto(Long id, String title, int pages, int height, int fontsize, String color, String fontcolor) {
        this.id = id;
        this.title = title;
        this.pages = pages;
        this.height = height;
        this.fontsize = fontsize;
        this.color = color;
        this.fontcolor = fontcolor;
    }

    /**
     * Constructor for new books.
     * @param title title of book.
     * @param pages number of pages.
     * @param height height of book.
     * @param fontsize font size for display.
     * @param color book color for display.
     * @param fontcolor font color for display.
     */
    public BookDto(String title, int pages, int height, int fontsize, String color, String fontcolor) {
        this.title = title;
        this.pages = pages;
        this.height = height;
        this.fontsize = fontsize;
        this.color = color;
        this.fontcolor = fontcolor;
    }

    /**
     * No-args constructor.
     */
    public BookDto() {
    }
}
