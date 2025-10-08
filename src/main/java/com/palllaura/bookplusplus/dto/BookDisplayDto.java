package com.palllaura.bookplusplus.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDisplayDto {
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
     * Book width in millimeters.
     */
    private int width;
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
     * X coordinate of book position.
     */
    private int xPosition;
    /**
     * Y coordinate of book position.
     */
    private int yPosition;

}
