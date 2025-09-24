package com.palllaura.bookplusplus;

import com.palllaura.bookplusplus.dto.BookLocationDto;
import com.palllaura.bookplusplus.dto.BookDto;
import com.palllaura.bookplusplus.entity.Book;
import com.palllaura.bookplusplus.repository.BookRepository;
import com.palllaura.bookplusplus.service.BookService;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

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
	 * Helper method to create a new book dto.
	 * @return dto.
	 */
	BookDto createNewBookDto() {
		BookDto dto = new BookDto();
		dto.setTitle("Six of Crows");
		dto.setPages(494);
		dto.setHeight(240);
		dto.setColor("#e20333");
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

	@Test
	void testAddNewBookCorrectTriggersSaveInRepository() {
		BookDto dto = createNewBookDto();
		service.addNewBook(dto);
		verify(repository, times(1)).save(any(Book.class));
	}

	@Test
	void testAddNewBookSavedBookHasCorrectTitle() {
		BookDto dto = createNewBookDto();
		ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
		service.addNewBook(dto);
		verify(repository, times(1)).save(captor.capture());

		Book savedBook = captor.getValue();
		Assertions.assertEquals(dto.getTitle(), savedBook.getTitle());
	}

	@Test
	void testAddNewBookSavedBookHasCorrectColor() {
		BookDto dto = createNewBookDto();
		ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
		service.addNewBook(dto);
		verify(repository, times(1)).save(captor.capture());

		Book savedBook = captor.getValue();
		Assertions.assertEquals(dto.getColor(), savedBook.getColor());
	}

	@Test
	void testAddNewBookSavedBookHasCorrectWidth() {
		BookDto dto = createNewBookDto();
		ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
		service.addNewBook(dto);
		verify(repository, times(1)).save(captor.capture());

		Book savedBook = captor.getValue();
		int correctWidth = dto.getPages() / 10;
		Assertions.assertEquals(correctWidth, savedBook.getBookWidthInMm());
	}

	@Test
	void testAddNewBookSavedBookHasCorrectInitialPosition() {
		BookDto dto = createNewBookDto();
		ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
		service.addNewBook(dto);
		verify(repository, times(1)).save(captor.capture());

		Book savedBook = captor.getValue();
		Assertions.assertEquals(10, savedBook.getXPosition());
		Assertions.assertEquals(10, savedBook.getYPosition());
	}

	@Test
	void testAddNewBookValidationFailsIfTitleIsBlank() {
		BookDto dto = createNewBookDto();
		dto.setTitle(" ");
		boolean result = service.addNewBook(dto);
		Assertions.assertFalse(result);
	}

	@Test
	void testAddNewBookValidationFailsIfColorIsMissing() {
		BookDto dto = createNewBookDto();
		dto.setColor(null);
		boolean result = service.addNewBook(dto);
		Assertions.assertFalse(result);
	}

	@Test
	void testAddNewBookValidationFailsIfNumberOfPagesIsNegative() {
		BookDto dto = createNewBookDto();
		dto.setPages(-5);
		boolean result = service.addNewBook(dto);
		Assertions.assertFalse(result);
	}

	@Test
	void testAddNewBookValidationFailsIfHeightIsIncorrect() {
		BookDto dto = createNewBookDto();
		dto.setHeight(-5);
		boolean result = service.addNewBook(dto);
		Assertions.assertFalse(result);
	}

}
