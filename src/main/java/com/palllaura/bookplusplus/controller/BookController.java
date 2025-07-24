package com.palllaura.bookplusplus.controller;

import com.palllaura.bookplusplus.entity.Book;
import com.palllaura.bookplusplus.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
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
}
