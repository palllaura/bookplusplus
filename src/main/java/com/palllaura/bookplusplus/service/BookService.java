package com.palllaura.bookplusplus.service;

import com.palllaura.bookplusplus.dto.BookLocationDto;
import com.palllaura.bookplusplus.dto.BookDto;
import com.palllaura.bookplusplus.entity.Book;
import com.palllaura.bookplusplus.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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
     * Find book by id.
     * @param id id of book.
     * @return optional of book.
     */
    public Book getBookById(Long id) {
        Optional<Book> bookOptional = repository.findById(id);
        if (bookOptional.isEmpty()) {
            throw new EntityNotFoundException("Book with id " + id + " not found");
        }
        return bookOptional.get();
    }

    /**
     * Try to update locations for all given books.
     * @param locations List of book location DTOs.
     */
    public void updateBookLocations(List<BookLocationDto> locations) {
        if (locations == null || locations.isEmpty()) return;

        Map<Long, BookLocationDto> locationDtoMap = locations.stream()
                .collect(Collectors.toMap(BookLocationDto::getId, Function.identity()));

        List<Long> ids = new ArrayList<>(locationDtoMap.keySet());

        List<Book> booksToSave = repository.findAllById(ids);

        booksToSave.forEach(book -> {
            BookLocationDto dto = locationDtoMap.get(book.getId());
            book.setXPosition(dto.getXPosition());
            book.setYPosition(dto.getYPosition());
        });

        Set<Long> foundIds = booksToSave.stream().map(Book::getId).collect(Collectors.toSet());
        locationDtoMap.keySet().stream()
                .filter(id -> !foundIds.contains(id))
                .forEach(id -> LOGGER.warn("Failed to update book with ID: {} , book not found", id));
        repository.saveAll(booksToSave);
    }

    /**
     * Save new book if all fields are valid.
     * @param dto book data.
     * @return true if book was saved, else false.
     */
    public boolean addNewBook(BookDto dto) {
        if (!validateBookDto(dto)) return false;

        int width = calculateBookWidth(dto.getPages());
        Book newBook = new Book(
                dto.getTitle(),
                dto.getPages(),
                width,
                dto.getHeight(),
                dto.getFontsize(),
                dto.getColor(),
                dto.getFontcolor(),
                10,
                10
        );

        try {
            repository.save(newBook);
            LOGGER.info("Saved new book: {}", newBook.getTitle());
            return true;
        } catch (Exception e) {
            LOGGER.warn("Exception while saving book: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Edit book if all fields are valid.
     * @param dto book data.
     * @return true if book was edited, else false.
     */
    public boolean editBook(BookDto dto) {
        if (!validateBookDto(dto)) return false;

        Optional<Book> bookOptional = repository.findById(dto.getId());
        if (bookOptional.isEmpty()) {
            LOGGER.warn("Failed to edit book with ID: {}, book not found", dto.getId());
            return false;
        }

        Book book = bookOptional.get();
        book.setTitle(dto.getTitle());
        book.setPages(dto.getPages());
        book.setBookWidthInMm(calculateBookWidth(dto.getPages()));
        book.setBookHeightInMm(dto.getHeight());
        book.setFontsize(dto.getFontsize());
        book.setColor(dto.getColor());
        book.setFontcolor(dto.getFontcolor());

        try {
            repository.save(book);
            LOGGER.info("Successfully edited book: {}", book.getTitle());
            return true;
        } catch (Exception e) {
            LOGGER.warn("Failed to update book with ID: {}", dto.getId());
            return false;
        }
    }

    /**
     * Validate new book fields.
     * @param dto new book data.
     * @return true if all fields are valid, else false.
     */
    private boolean validateBookDto(BookDto dto) {
        boolean isValid = true;
        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            LOGGER.info("Book title is missing or blank");
            isValid = false;
        }
        if (dto.getColor() == null || dto.getColor().isBlank()) {
            LOGGER.info("Book color is missing or blank");
            isValid = false;
        }
        if (dto.getFontcolor() == null || dto.getFontcolor().isBlank()) {
            LOGGER.info("Book title color is missing or blank");
            isValid = false;
        }
        if (dto.getPages() < 10 || dto.getPages() > 1000) {
            LOGGER.info("Number of pages is incorrect: {}", dto.getPages());
            isValid = false;
        }
        if (dto.getFontsize() < 2 || dto.getFontsize() > 100) {
            LOGGER.info("Font size is incorrect: {}", dto.getFontsize());
            isValid = false;
        }
        if (dto.getHeight() < 100 || dto.getHeight() > 1000) {
            LOGGER.info("Book height is incorrect: {}", dto.getHeight());
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
