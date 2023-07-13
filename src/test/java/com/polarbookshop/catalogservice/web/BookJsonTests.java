package com.polarbookshop.catalogservice.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import com.polarbookshop.catalogservice.domain.Book;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

@JsonTest
class BookJsonTests {

  @Autowired
  private JacksonTester<Book> json;

  @Test
  void testSerialize() throws Exception {
    var now = Instant.now();
    var book = new Book(10L, "1234567890", "Title", "Author", 9.90, "Publisher", now, now, 1);
    var jsonContent = json.write(book);
    assertThat(jsonContent).extractingJsonPathNumberValue("@.id")
      .isEqualTo(book.id().intValue());
    assertThat(jsonContent).extractingJsonPathStringValue("@.isbn")
      .isEqualTo(book.isbn());
    assertThat(jsonContent).extractingJsonPathStringValue("@.title")
      .isEqualTo(book.title());
    assertThat(jsonContent).extractingJsonPathStringValue("@.author")
      .isEqualTo(book.author());
    assertThat(jsonContent).extractingJsonPathNumberValue("@.price")
      .isEqualTo(book.price());
    assertThat(jsonContent).extractingJsonPathStringValue("@.publisher")
      .isEqualTo(book.publisher());
    assertThat(jsonContent).extractingJsonPathNumberValue("@.version")
      .isEqualTo(book.version());
  }

  @Test
  void testDeserialize() throws Exception {
    var instant = Instant.parse("2021-09-07T22:50:37.135029Z");
    var content = """
        {
          "id": 10,
          "isbn": "1234567890",
          "title": "Title",
          "author": "Author",
          "price": 9.90,
          "publisher": "Publisher",
          "createdDate": "2021-09-07T22:50:37.135029Z",
          "lastModifiedDate": "2021-09-07T22:50:37.135029Z",
          "version": 1
        }
        """;
    assertThat(json.parse(content))
        .usingRecursiveComparison()
        .isEqualTo(new Book(10L, "1234567890", "Title", "Author", 9.90, "Publisher", instant, instant, 1));
  }
}
