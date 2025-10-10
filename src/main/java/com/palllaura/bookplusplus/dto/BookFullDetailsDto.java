package com.palllaura.bookplusplus.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookFullDetailsDto {
    /**
     * ID of book.
     */
    private Long id;
    /**
     * Title of book.
     */
    private String title;
    /**
     * Book height in millimeters.
     */
    private int height;
    /**
     * Number of pages.
     */
    private int pages;
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

}
