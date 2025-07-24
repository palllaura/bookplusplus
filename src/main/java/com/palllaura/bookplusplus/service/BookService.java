package com.palllaura.bookplusplus.service;

import com.palllaura.bookplusplus.entity.Book;
import com.palllaura.bookplusplus.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
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

}
