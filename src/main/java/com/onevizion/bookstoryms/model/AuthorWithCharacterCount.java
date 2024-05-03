package com.onevizion.bookstoryms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorWithCharacterCount {

    private String author;

    private int characterCount;
}
