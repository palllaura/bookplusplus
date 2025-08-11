package com.palllaura.bookplusplus.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewBookDto {
    private String title;
    /**
     * Book width in millimeters.
     */
    private int width;
    /**
     * Book height in millimeters.
     */
    private int height;
    /**
     * Color code of book.
     */
    private String color;

}
