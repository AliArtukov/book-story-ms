package com.onevizion.bookstoryms.controller;

import com.onevizion.bookstoryms.entity.Book;
import com.onevizion.bookstoryms.model.AuthorWithCharacterCount;
import com.onevizion.bookstoryms.service.contract.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/v1/books", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Book", description = "Контроллер для книг")
public class BookController {

    private final BookService bookService;

    @GetMapping
    @Operation(summary = "endpoint возвращающий список все книг, которые содержатся в таблице book, отсортированный в обратном алфавитном порядке значения колонки book.title")
    public ResponseEntity<List<Book>> getAllBooksOrderedByTitleDesc() {
        return bookService.getAllBooksOrderedByTitleDesc();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "endpoint добавления новой книги в таблицу book")
    public ResponseEntity<Long> addBook(@RequestBody @Valid final Book book) {
        return bookService.addBook(book);
    }

    @GetMapping("/grouped-by-author")
    @Operation(summary = "endpoint возвращающий список всех книг, сгруппированных по автору книги(book.author)")
    public ResponseEntity<Map<String, List<Book>>> getBooksGroupedByAuthor() {
        return bookService.getBooksGroupedByAuthor();
    }

    @GetMapping("/authors-with-most-common-character")
    @Operation(summary = """
            endpoint принимающий в качестве параметра символ и возвращающий список из 10 авторов,
               в названии книг которых этот символ встречается наибольшее количество раз вместе с
               количеством вхождений этого символа во все названия книг автора.
               Регистр символа не имеет значения. Авторы, в названии книг которых символ отсутствует, не
               должны присутствовать в возвращаемом значении.
               Пример:
               Входной параметр: “а”
               Результат: [{L. Tolstoy, 7},{F. Dostoevsky, 4},{N. Gogol, 1}]
            """)
    public ResponseEntity<List<AuthorWithCharacterCount>> getAuthorsWithMostCommonCharacter(@RequestParam char character) {
        return bookService.getAuthorsWithMostCommonCharacter(character);
    }
}
