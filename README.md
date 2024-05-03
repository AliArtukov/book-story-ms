# book-story-ms

## Тестовое задание - JAVA

Предполагается, что существует база данных с таблицей book:

```sql
CREATE TABLE IF NOT EXISTS book (
                                   id SERIAL PRIMARY KEY,
                                   title VARCHAR(150) NOT NULL,
                                   author VARCHAR(150) NOT NULL,
                                   description VARCHAR(150)
);
```

### Тестовые данные:

```sql
INSERT INTO book (title, author, description)
VALUES ('Crime and Punishment', 'F. Dostoevsky', NULL),
       ('Anna Karenina', 'L. Tolstoy', NULL),
       ('The Brothers Karamazov', 'F. Dostoevsky', NULL),
       ('War and Peace', 'L. Tolstoy', NULL),
       ('Dead Souls', 'N. Gogol', NULL);
```

### Необходимо реализовать контроллер с 4мя endpoints:

1. endpoint возвращающий список все книг, которые содержатся в таблице book,
   отсортированный в обратном алфавитном порядке значения колонки `book.title`
2. endpoint добавления новой книги в таблицу book
3. endpoint возвращающий список всех книг, сгруппированных по автору
   книги `book.author`
4. endpoint принимающий в качестве параметра символ и возвращающий список из 10 авторов,
   в названии книг которых этот символ встречается наибольшее количество раз вместе с
   количеством вхождений этого символа во все названия книг автора.
   Регистр символа не имеет значения. Авторы, в названии книг которых символ отсутствует, не
   должны присутствовать в возвращаемом значении.
   Пример:
   Входной параметр: `а`
   Результат: `[{L. Tolstoy, 7},{F. Dostoevsky, 4},{N. Gogol, 1}]`

### Требования к коду проекта:

1. для реализации необходимо использовать Spring Framework.
2. endpoints должны соответствовать RESTful Resource Naming Convention.
3. endpoints должны получать\возвращать JSON объекты.
4. для доступа к базе данных необходимо использовать JdbcTemplate из Spring Framework.
   5.в одном из endpoint обязательно применить Java Stream API.
   6.готовый проект залить в GitHub.