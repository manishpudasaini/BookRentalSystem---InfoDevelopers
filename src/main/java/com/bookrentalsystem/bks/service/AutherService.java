package com.bookrentalsystem.bks.service;

import com.bookrentalsystem.bks.dto.author.AuthorRequest;
import com.bookrentalsystem.bks.dto.author.AuthorResponse;
import com.bookrentalsystem.bks.model.Author;
import com.bookrentalsystem.bks.repo.AuthorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AutherService {

    private final AuthorRepo authorRepo;

    public AuthorResponse addAuthor(AuthorRequest authorRequest){
        Author author = authorRequestToEntity(authorRequest);
         authorRepo.save(author);

        return entityToAuthorResponse(author);
    }

    public Author authorRequestToEntity(AuthorRequest authorRequest){
        return Author.builder()
                .name(authorRequest.getName())
                .email(authorRequest.getEmail())
                .number(authorRequest.getNumber()).build();
    }
    public AuthorResponse entityToAuthorResponse(Author author){
        return AuthorResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .number(author.getNumber())
                .build();
    }

    public List<Author> convertToAuthorList(List<Short> ids){
        List<Author> authorList =  ids.stream().map(id -> authorRepo.findById(id).orElseThrow()).collect(Collectors.toList());
        return authorList;
    }

    public List<AuthorResponse> convertAuthorResponseList(List<Author> authors){
        List<AuthorResponse> authorResponseList =  authors.stream()
                .map(auth -> entityToAuthorResponse(auth)).collect(Collectors.toList());
        return authorResponseList;
    }
}
