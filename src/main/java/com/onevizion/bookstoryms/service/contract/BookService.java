package com.onevizion.bookstoryms.service.contract;

import com.onevizion.bookstoryms.entity.Book;
import com.onevizion.bookstoryms.model.AuthorWithCharacterCount;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;


public interface BookService {

    ResponseEntity<List<Book>> getAllBooksOrderedByTitleDesc();

    ResponseEntity<Long> addBook(final Book book);

    ResponseEntity<Map<String, List<Book>>> getBooksGroupedByAuthor();

    ResponseEntity<List<AuthorWithCharacterCount>> getAuthorsWithMostCommonCharacter(char character);
}
