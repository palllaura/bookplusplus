package com.palllaura.bookplusplus.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewBookDto {
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

}
