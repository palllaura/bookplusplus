package com.palllaura.bookplusplus.service;

import com.palllaura.bookplusplus.dto.BookLocationDto;
import com.palllaura.bookplusplus.dto.NewBookDto;
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

    /**
     * Save new book if all fields are valid.
     * @param dto book data.
     * @return true if book was saved, else false.
     */
    public boolean addNewBook(NewBookDto dto) {
        if (!validateNewBookDto(dto)) return false;

        Book newBook = new Book(
                dto.getTitle(),
                dto.getWidth(),
                dto.getHeight(),
                dto.getColor(),
                100,
                100
        );

        try {
            repository.save(newBook);
            LOGGER.info("Saved new book: " + newBook.getTitle());
            return true;
        } catch (Exception e) {
            LOGGER.warning("Exception while saving book: " + e.getMessage());
            return false;
        }
    }

    /**
     * Validate new book fields.
     * @param dto new book data.
     * @return true if all fields are valid, else false.
     */
    private boolean validateNewBookDto(NewBookDto dto) {
        boolean isValid = true;
        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            LOGGER.info("Book title is missing or blank");
            isValid = false;
        }
        if (dto.getColor() == null || dto.getColor().isBlank()) {
            LOGGER.info("Book color is missing or blank");
            isValid = false;
        }
        if (dto.getWidth() < 10 || dto.getWidth() > 3000) {
            LOGGER.info("Book width is incorrect");
            isValid = false;
        }
        if (dto.getHeight() < 100 || dto.getHeight() > 1000) {
            LOGGER.info("Book height is incorrect");
            isValid = false;
        }
        return isValid;
    }

}
