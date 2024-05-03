package com.onevizion.bookstoryms.service.implementation;

import com.onevizion.bookstoryms.entity.Book;
import com.onevizion.bookstoryms.model.AuthorWithCharacterCount;
import com.onevizion.bookstoryms.service.contract.BookService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final JdbcTemplate jdbcTemplate;

    public ResponseEntity<List<Book>> getAllBooksOrderedByTitleDesc() {
        try {
            List<Book> result = jdbcTemplate.query("SELECT * FROM book ORDER BY title DESC",
                    new BookRowMapper());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Long> addBook(final Book book) {
        try {
            jdbcTemplate.update("INSERT INTO book (title, author, description) VALUES (?, ?, ?)",
                    book.getTitle(), book.getAuthor(), book.getDescription());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<Map<String, List<Book>>> getBooksGroupedByAuthor() {
        try {
            List<Book> allBooks = jdbcTemplate.query("SELECT * FROM book",
                    new BookRowMapper());
            Map<String, List<Book>> result = allBooks.stream()
                    .collect(Collectors.groupingBy(Book::getAuthor));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<List<AuthorWithCharacterCount>> getAuthorsWithMostCommonCharacter(char character) {
        try {
            List<Book> allBooks = jdbcTemplate.query("SELECT * FROM book",
                    new BookRowMapper());
            List<AuthorWithCharacterCount> result = allBooks.stream()
                    .collect(Collectors.groupingBy(
                            Book::getAuthor,
                            Collectors.summingInt(book -> countCharacterOccurrences(book.getTitle(), character))))
                    .entrySet().stream().
                    filter(entry -> entry.getValue() > 0)
                    .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                    .limit(10)
                    .map(entry -> new AuthorWithCharacterCount(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setId(rs.getLong("id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setDescription(rs.getString("description"));
            return book;
        }
    }

    private static int countCharacterOccurrences(String str, char character) {
        return (int) str.chars().map(Character::toLowerCase).filter(ch -> ch == Character.toLowerCase(character)).count();
    }
}
