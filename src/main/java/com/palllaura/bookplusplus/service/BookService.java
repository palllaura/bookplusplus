package com.palllaura.bookplusplus.service;

import com.palllaura.bookplusplus.dto.BookLocationDto;
import com.palllaura.bookplusplus.entity.Book;
import com.palllaura.bookplusplus.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class BookService {
    private static final Logger LOGGER = Logger.getLogger(BookService.class.getName());
    private final BookRepository repository;

    /**
     * Book service constructor.
     * @param repository BookRepository.
     */
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    /**
     * Get all books saved in database.
     * @return books in a list.
     */
    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    /**
     * Try to update locations for all given books.
     * @param locations List of book location DTOs.
     */
    public void updateBookLocations(List<BookLocationDto> locations) {
        List<Book> booksToSave = new ArrayList<>();

        for (BookLocationDto dto : locations) {
            Optional<Book> bookOptional = repository.findById(dto.getId());
            if (bookOptional.isEmpty()) {
                LOGGER.warning("Failed to update book with ID: " + dto.getId() + ", book not found");
                continue;
            }
            Book book = bookOptional.get();
            book.setXPosition(dto.getXPosition());
            book.setYPosition(dto.getYPosition());
            booksToSave.add(book);
        }
        repository.saveAll(booksToSave);
    }

}
