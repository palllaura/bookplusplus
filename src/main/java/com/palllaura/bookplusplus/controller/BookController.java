package com.palllaura.bookplusplus.controller;

import com.palllaura.bookplusplus.dto.BookLocationDto;
import com.palllaura.bookplusplus.entity.Book;
import com.palllaura.bookplusplus.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
    private final BookService service;

    /**
     * Book controller constructor.
     * @param service BookService.
     */
    public BookController(BookService service) {
        this.service = service;
    }

    /**
     * Get all saved books.
     * @return books in a list.
     */
    @GetMapping("/allBooks")
    public List<Book> getAllBooks() {
        return service.getAllBooks();
    }

    /**
     * Update locations for all given books.
     * @param locations List of DTOs with new locations.
     */
    @PostMapping("/saveLocations")
    public void saveBookLocations(@RequestBody List<BookLocationDto> locations) {
        service.updateBookLocations(locations);
    }
}
