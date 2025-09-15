package com.palllaura.bookplusplus.service;

import com.palllaura.bookplusplus.dto.BookLocationDto;
import com.palllaura.bookplusplus.dto.NewBookDto;
import com.palllaura.bookplusplus.entity.Book;
import com.palllaura.bookplusplus.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);
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
                String message = String.format("Failed to update book with ID: %1$s , book not found", dto.getId());
                LOGGER.warn(message);
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

        int width = calculateBookWidth(dto.getPages());
        Book newBook = new Book(
                dto.getTitle(),
                dto.getPages(),
                width,
                dto.getHeight(),
                dto.getFontsize(),
                dto.getColor(),
                10,
                10
        );

        try {
            repository.save(newBook);
            String message = String.format("Saved new book: %1$s", newBook.getTitle());
            LOGGER.info(message);
            return true;
        } catch (Exception e) {
            String message = String.format("Exception while saving book:: %1$s", e.getMessage());
            LOGGER.warn(message);
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
        if (dto.getPages() < 10 || dto.getPages() > 1000) {
            LOGGER.info("Number of pages is incorrect");
            isValid = false;
        }
        if (dto.getHeight() < 100 || dto.getHeight() > 1000) {
            LOGGER.info("Book height is incorrect");
            isValid = false;
        }
        return isValid;
    }

    /**
     * Calculate book width from number of pages.
     * @param pages number of pages.
     * @return book width.
     */
    private int calculateBookWidth(int pages) {
        return pages / 10;
    }

}
