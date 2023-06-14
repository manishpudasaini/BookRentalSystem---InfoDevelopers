package com.bookrentalsystem.bks.service;

import com.bookrentalsystem.bks.dto.author.AuthorRequest;
import com.bookrentalsystem.bks.dto.author.AuthorResponse;
import com.bookrentalsystem.bks.model.Author;

import java.util.List;

public interface AuthorService {
     AuthorResponse addAuthor(AuthorRequest authorRequest);
     Author authorRequestToEntity(AuthorRequest authorRequest);
     AuthorResponse entityToAuthorResponse(Author author);
     List<Author> convertToAuthorList(List<Short> ids);
     List<AuthorResponse> convertToAuthorResponseList(List<Author> authors);
     Author findAuthorById(short id);
}
