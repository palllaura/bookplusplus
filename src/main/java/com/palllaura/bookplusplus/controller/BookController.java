package com.palllaura.bookplusplus.controller;

import com.palllaura.bookplusplus.dto.BookLocationDto;
import com.palllaura.bookplusplus.dto.BookDto;
import com.palllaura.bookplusplus.entity.Book;
import com.palllaura.bookplusplus.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
     * Get book by id.
     * @param id id of book.
     * @return book.
     */
    @GetMapping("/fetchBook/{id}")
    public Book getBookById(@PathVariable Long id) {
        return service.getBookById(id);
    }

    /**
     * Update locations for all given books.
     * @param locations List of DTOs with new locations.
     */
    @PostMapping("/saveLocations")
    public void saveBookLocations(@RequestBody List<BookLocationDto> locations) {
        service.updateBookLocations(locations);
    }

    /**
     * Add new book.
     * @param dto new book data.
     * @return response entity with result message.
     */
    @PostMapping("/addBook")
    public ResponseEntity<?> addBook(@RequestBody BookDto dto) {
        if (service.addNewBook(dto)) {
            return ResponseEntity.ok(Map.of(
                    "message", "Book added successfully"
            ));
        } else {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "Failed to save book"
            ));
        }
    }

    /**
     * Edit existing book.
     * @param dto dto with book data.
     * @return response entity with result message.
     */
    @PostMapping("/editBook")
    public ResponseEntity<?> editLocation(@RequestBody BookDto dto) {
        if (service.editBook(dto)) {
            return ResponseEntity.ok(Map.of(
                    "message", "Book edited successfully!"
            ));
        } else {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "Failed to edit book."
            ));
        }
    }


}
