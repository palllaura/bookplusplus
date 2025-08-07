package com.palllaura.bookplusplus.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookLocationDto {
    /**
     * Unique ID of each book.
     */
    private Long id;
    /**
     * X coordinate of book position.
     */
    private int xPosition;
    /**
     * Y coordinate of book position.
     */
    private int yPosition;
}
