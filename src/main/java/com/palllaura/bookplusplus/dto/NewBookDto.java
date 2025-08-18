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
     * Color code of book.
     */
    private String color;

}
