package com.bookrentalsystem.bks.service.AuthorServiceImpl;

import com.bookrentalsystem.bks.dto.author.AuthorRequest;
import com.bookrentalsystem.bks.dto.author.AuthorResponse;
import com.bookrentalsystem.bks.exception.AuthorNotfoundException;
import com.bookrentalsystem.bks.model.Author;
import com.bookrentalsystem.bks.repo.AuthorRepo;
import com.bookrentalsystem.bks.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepo authorRepo;

    public AuthorResponse addAuthor(AuthorRequest authorRequest){
        Author author = authorRequestToEntity(authorRequest);
         authorRepo.save(author);

        return entityToAuthorResponse(author);
    }

    public Author authorRequestToEntity(AuthorRequest authorRequest){
        Author author = new Author();
        author.setName(authorRequest.getName());
        author.setEmail(authorRequest.getEmail());
        author.setNumber(authorRequest.getNumber());
        return author;
    }

    /*
    * converting the Author entity to AuthorResponse
     */
    public AuthorResponse entityToAuthorResponse(Author author){
        return AuthorResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .number(author.getNumber())
                .build();
    }

    /*
    * from the id finding the author
    * And collecting it to seprate Author list
    * returning the author list
     */
    public List<Author> convertToAuthorList(List<Short> ids){
        List<Author> authorList =  ids.stream().map(id -> findAuthorById(id)).collect(Collectors.toList());
        return authorList;
    }

    /*
    *converting the author list to AuthorResponse list
     */
    public List<AuthorResponse> convertToAuthorResponseList(List<Author> authors){
        List<AuthorResponse> authorResponseList =  authors.stream()
                .map(auth -> entityToAuthorResponse(auth)).collect(Collectors.toList());
        return authorResponseList;
    }

    public Author findAuthorById(short id)  {
        Optional<Author> singleAuthor = authorRepo.findById(id);
        if(singleAuthor.isPresent()){
            Author author = singleAuthor.get();
            return author;
        }
        throw new AuthorNotfoundException("Author having this"+ id +" doesnot exist!!!");
    }
}
