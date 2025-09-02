package com.palllaura.bookplusplus;

import com.palllaura.bookplusplus.dto.BookLocationDto;
import com.palllaura.bookplusplus.entity.Book;
import com.palllaura.bookplusplus.repository.BookRepository;
import com.palllaura.bookplusplus.service.BookService;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BookServiceTests {

	private BookService service;
	private BookRepository repository;
	private LogCaptor logCaptor;

	@BeforeEach
	void setUp() {
		repository = mock(BookRepository.class);
		service = new BookService(repository);
		logCaptor = LogCaptor.forClass(BookService.class);

	}

	@Test
	void testGetAllBooksTriggersCorrectMethodInRepository() {
		service.getAllBooks();
		verify(repository, times(1)).findAll();
	}

	/**
	 * Helper method to create a BookLocationDto.
	 * @return dto.
	 */
	BookLocationDto createBookLocationDto() {
		BookLocationDto dto = new BookLocationDto();
		dto.setId(1L);
		dto.setXPosition(5);
		dto.setYPosition(15);
		return dto;
	}

	/**
	 * Helper method to create a new Book.
	 * @return book.
	 */
	Book createBook() {
		Book book = new Book();
		book.setId(1L);
		book.setXPosition(10);
		book.setYPosition(100);
		return book;
	}

	@Test
	void testUpdateBookLocationsCorrectTriggersCorrectMethodInRepository() {
		BookLocationDto dto = createBookLocationDto();
		Book book = createBook();

		when(repository.findById(1L)).thenReturn(Optional.ofNullable(book));
		service.updateBookLocations(List.of(dto));

		verify(repository, times(1)).saveAll(any());
	}

	@Test
	void testUpdateBookLocationsCorrectLocationsAreUpdated() {
		BookLocationDto dto = createBookLocationDto();
		Book book = createBook();

		when(repository.findById(1L)).thenReturn(Optional.ofNullable(book));
		service.updateBookLocations(List.of(dto));

		Assertions.assertEquals(5, book.getXPosition());
		Assertions.assertEquals(15, book.getYPosition());
	}

	@Test
	void testUpdateBookLocationsBookNotFoundCorrectWarning() {
		BookLocationDto dto = createBookLocationDto();
		when(repository.findById(1L)).thenReturn(Optional.empty());

		service.updateBookLocations(List.of(dto));

		Assertions.assertTrue(
				logCaptor.getWarnLogs().stream()
						.anyMatch(msg -> msg.contains("Failed to update book with ID: 1")),
				"Expected warning log was not found"
		);
	}

}
