package com.palllaura.bookplusplus;

import com.palllaura.bookplusplus.dto.BookLocationDto;
import com.palllaura.bookplusplus.dto.BookDto;
import com.palllaura.bookplusplus.entity.Book;
import com.palllaura.bookplusplus.repository.BookRepository;
import com.palllaura.bookplusplus.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTests {

	@Mock
	private BookRepository repository;
	@InjectMocks
	private BookService service;

	private LogCaptor logCaptor;

	@BeforeEach
	void setUp() {
		logCaptor = LogCaptor.forClass(BookService.class);
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
	BookDto createValidBookDto() {
		BookDto dto = new BookDto();
		dto.setTitle("Six of Crows");
		dto.setPages(494);
		dto.setHeight(240);
		dto.setColor("#e20333");
		dto.setFontcolor("#ffffff");
		dto.setFontsize(16);
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
	void testGetAllBooksTriggersCorrectMethodInRepository() {
		service.getAllBooks();
		verify(repository, times(1)).findAll();
	}

	@Test
	void testGetBookByIdReturnsCorrectBook() {
		Book book = createBook();
		when(repository.findById(1L)).thenReturn(Optional.of(book));
		Book result = service.getBookById(1L);
		Assertions.assertEquals(result, book);
	}

	@Test
	void testGetBookByIdThrowsExceptionWhenIdNotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());
		Assertions.assertThrows(EntityNotFoundException.class, () -> service.getBookById(1L));
	}

	@Test
	void testUpdateBookLocationsDoesntTriggerRepositoryWhenLocationsIsNull() {
		service.updateBookLocations(null);
		verifyNoInteractions(repository);
	}

	@Test
	void testUpdateBookLocationsDoesntTriggerRepositoryWhenLocationsIsEmpty() {
		service.updateBookLocations(List.of());
		verifyNoInteractions(repository);
	}

	@Test
	void testUpdateBookLocationsCorrectTriggersCorrectMethodInRepository() {
		BookLocationDto dto = createBookLocationDto();
		Book book = createBook();

		when(repository.findAllById(List.of(1L))).thenReturn(List.of(book));
		service.updateBookLocations(List.of(dto));

		verify(repository, times(1)).saveAll(any());
	}

	@Test
	void testUpdateBookLocationsCorrectLocationsAreUpdated() {
		BookLocationDto dto = createBookLocationDto();
		Book book = createBook();

		when(repository.findAllById(List.of(1L))).thenReturn(List.of(book));
		service.updateBookLocations(List.of(dto));

		Assertions.assertEquals(5, book.getXPosition());
		Assertions.assertEquals(15, book.getYPosition());
	}

	@Test
	void testUpdateBookLocationsBookNotFoundCorrectWarning() {
		BookLocationDto dto = createBookLocationDto();
		when(repository.findAllById(List.of(1L))).thenReturn(List.of());

		service.updateBookLocations(List.of(dto));

		Assertions.assertTrue(
				logCaptor.getWarnLogs().stream()
						.anyMatch(msg -> msg.contains("Failed to update book with ID: 1")),
				"Expected warning log was not found"
		);
	}

	@Test
	void testAddNewBookCorrectTriggersSaveInRepository() {
		BookDto dto = createValidBookDto();
		service.addNewBook(dto);
		verify(repository, times(1)).save(any(Book.class));
	}

	@Test
	void testAddNewBookSavedBookHasCorrectTitle() {
		BookDto dto = createValidBookDto();
		ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
		service.addNewBook(dto);
		verify(repository, times(1)).save(captor.capture());

		Book savedBook = captor.getValue();
		Assertions.assertEquals(dto.getTitle(), savedBook.getTitle());
	}

	@Test
	void testAddNewBookSavedBookHasCorrectColor() {
		BookDto dto = createValidBookDto();
		ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
		service.addNewBook(dto);
		verify(repository, times(1)).save(captor.capture());

		Book savedBook = captor.getValue();
		Assertions.assertEquals(dto.getColor(), savedBook.getColor());
	}

	@Test
	void testAddNewBookSavedBookHasCorrectWidth() {
		BookDto dto = createValidBookDto();
		ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
		service.addNewBook(dto);
		verify(repository, times(1)).save(captor.capture());

		Book savedBook = captor.getValue();
		int correctWidth = dto.getPages() / 10;
		Assertions.assertEquals(correctWidth, savedBook.getBookWidthInMm());
	}

	@Test
	void testAddNewBookSavedBookHasCorrectInitialPosition() {
		BookDto dto = createValidBookDto();
		ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
		service.addNewBook(dto);
		verify(repository, times(1)).save(captor.capture());

		Book savedBook = captor.getValue();
		Assertions.assertEquals(10, savedBook.getXPosition());
		Assertions.assertEquals(10, savedBook.getYPosition());
	}

	@Test
	void testAddNewBookValidationFailsIfTitleIsBlank() {
		BookDto dto = createValidBookDto();
		dto.setTitle(" ");
		boolean result = service.addNewBook(dto);
		Assertions.assertFalse(result);
	}

	@Test
	void testAddNewBookValidationFailsIfTitleIsMissing() {
		BookDto dto = createValidBookDto();
		dto.setTitle(null);
		boolean result = service.addNewBook(dto);
		Assertions.assertFalse(result);
	}

	@Test
	void testAddNewBookValidationFailsIfColorIsMissing() {
		BookDto dto = createValidBookDto();
		dto.setColor(null);
		boolean result = service.addNewBook(dto);
		Assertions.assertFalse(result);
	}

	@Test
	void testAddNewBookValidationFailsIfColorIsBlank() {
		BookDto dto = createValidBookDto();
		dto.setColor(" ");
		boolean result = service.addNewBook(dto);
		Assertions.assertFalse(result);
	}

	@Test
	void testAddNewBookValidationFailsIfFontColorIsMissing() {
		BookDto dto = createValidBookDto();
		dto.setFontcolor(null);
		boolean result = service.addNewBook(dto);
		Assertions.assertFalse(result);
	}

	@Test
	void testAddNewBookValidationFailsIfFontColorIsBlank() {
		BookDto dto = createValidBookDto();
		dto.setFontcolor("       ");
		boolean result = service.addNewBook(dto);
		Assertions.assertFalse(result);
	}

	@Test
	void testAddNewBookValidationFailsIfNumberOfPagesIsNegative() {
		BookDto dto = createValidBookDto();
		dto.setPages(-5);
		boolean result = service.addNewBook(dto);
		Assertions.assertFalse(result);
	}

	@Test
	void testAddNewBookValidationFailsIfNumberOfPagesIsTooLarge() {
		BookDto dto = createValidBookDto();
		dto.setPages(Integer.MAX_VALUE);
		boolean result = service.addNewBook(dto);
		Assertions.assertFalse(result);
	}

	@Test
	void testAddNewBookValidationFailsIfFontSizeIsNegative() {
		BookDto dto = createValidBookDto();
		dto.setFontsize(-5);
		boolean result = service.addNewBook(dto);
		Assertions.assertFalse(result);
	}

	@Test
	void testAddNewBookValidationFailsIfFontSizeIsTooLarge() {
		BookDto dto = createValidBookDto();
		dto.setFontsize(Integer.MAX_VALUE);
		boolean result = service.addNewBook(dto);
		Assertions.assertFalse(result);
	}

	@Test
	void testAddNewBookValidationFailsIfHeightIsNegative() {
		BookDto dto = createValidBookDto();
		dto.setHeight(-5);
		boolean result = service.addNewBook(dto);
		Assertions.assertFalse(result);
	}

	@Test
	void testAddNewBookValidationFailsIfHeightIsTooLarge() {
		BookDto dto = createValidBookDto();
		dto.setHeight(Integer.MAX_VALUE);
		boolean result = service.addNewBook(dto);
		Assertions.assertFalse(result);
	}

	@Test
	void testEditBookCorrectTriggersSaveInRepository() {
		Book book = createBook();
		BookDto dto = createValidBookDto();
		dto.setId(1L);
		when(repository.findById(1L)).thenReturn(Optional.of(book));
		service.editBook(dto);
		verify(repository, times(1)).save(any(Book.class));
	}

	@Test
	void testEditBookSavedBookHasCorrectTitle() {
		Book book = createBook();
		BookDto dto = createValidBookDto();
		dto.setId(1L);
		when(repository.findById(1L)).thenReturn(Optional.of(book));

		ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
		service.editBook(dto);
		verify(repository, times(1)).save(captor.capture());

		Book savedBook = captor.getValue();
		Assertions.assertEquals(dto.getTitle(), savedBook.getTitle());
	}

	@Test
	void testEditBookSavedBookHasCorrectColor() {
		Book book = createBook();
		BookDto dto = createValidBookDto();
		dto.setId(1L);
		when(repository.findById(1L)).thenReturn(Optional.of(book));

		ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
		service.editBook(dto);
		verify(repository, times(1)).save(captor.capture());

		Book savedBook = captor.getValue();
		Assertions.assertEquals(dto.getColor(), savedBook.getColor());
	}

	@Test
	void testEditBookSavedBookHasCorrectWidth() {
		Book book = createBook();
		BookDto dto = createValidBookDto();
		dto.setId(1L);
		when(repository.findById(1L)).thenReturn(Optional.of(book));

		ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
		service.editBook(dto);
		verify(repository, times(1)).save(captor.capture());

		Book savedBook = captor.getValue();
		int correctWidth = dto.getPages() / 10;
		Assertions.assertEquals(correctWidth, savedBook.getBookWidthInMm());
	}

	@Test
	void testEditBookBookNotFoundCorrectWarning() {
		BookDto dto = createValidBookDto();
		dto.setId(1L);
		when(repository.findById(1L)).thenReturn(Optional.empty());

		service.editBook(dto);
		Assertions.assertTrue(
				logCaptor.getWarnLogs().stream()
						.anyMatch(msg -> msg.contains("Failed to edit book with ID: 1, book not found")),
				"Expected warning log was not found"
		);
	}

	@Test
	void testEditBookValidationFailsReturnsFalse() {
		BookDto dto = createValidBookDto();
		dto.setId(1L);
		dto.setPages(-1);
		boolean result = service.editBook(dto);
		Assertions.assertFalse(result);
	}
}
