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
public class AuthorServiceImpl implements AuthorService  {

    private final AuthorRepo authorRepo;
    //this method is used to add the author in our database
    public String addAuthorDb(AuthorRequest authorRequest){
        Optional<Author> dbAuthorFalse = authorRepo.findByEmailAndDeletedIsFalse(authorRequest.getEmail());
        Optional<Author> dbAuthorTrue = authorRepo.findByEmailAndDeletedIsTrue(authorRequest.getEmail());
        Author author = authorRequestToEntity(authorRequest);

        if(dbAuthorFalse.isPresent()  ){
            Author activeAuthor = dbAuthorFalse.get();
            if(author.getEmail().equals(activeAuthor.getEmail())
                    || author.getNumber().equals(activeAuthor.getNumber())){
                return "Author already exist!!";
            }
        }
        if(dbAuthorTrue.isPresent()){
            Author inActiveAuthor = dbAuthorTrue.get();
            if(inActiveAuthor.getEmail().equals(authorRequest.getEmail()) ||
                    inActiveAuthor.getNumber().equals(authorRequest.getNumber())){
                return "Author already exist!!";
            }
        }
         authorRepo.save(author);
        return "added successfully";
    }

    @Override
    public String updateAuthorAdd(AuthorRequest authorRequest) {
        Author author = authorRequestToEntity(authorRequest);
        authorRepo.save(author);
        return "updated";
    }

    //this method take parameter as AuthorRequest & convert the request into Author entity
    public Author authorRequestToEntity(AuthorRequest authorRequest){
        Author author = new Author();
        author.setId(authorRequest.getId());
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
        return ids.stream().map(this::findAuthorById).collect(Collectors.toList());
    }

    /*
    *converting the author list to AuthorResponse list
     */
    public List<AuthorResponse> convertToAuthorResponseList(List<Author> authors){
        return authors.stream()
                .map(this::entityToAuthorResponse).collect(Collectors.toList());
    }

    //this method is used to find all the author list and return it in AuthorResponse form
    @Override
    public List<AuthorResponse> allAuthor() {
        List<Author> allAuthors = authorRepo.findAll();
        return convertToAuthorResponseList(allAuthors);
    }

    //take parameter as Id & return Author
    public Author findAuthorById(short id)  {
        Optional<Author> singleAuthor = authorRepo.findById(id);
        if(singleAuthor.isPresent()){
            return singleAuthor.get();
        }
        throw new AuthorNotfoundException("Author having this"+ id +" doesnot exist!!!");
    }

    //take parameter as Id  & return AuthorResponse
    @Override
    public AuthorResponse findAuthorResponseById(Short id) {
       Optional<Author> singleAuthor =  authorRepo.findById(id);
       if(singleAuthor.isPresent()){
           Author author = singleAuthor.get();
           return entityToAuthorResponse(author);
       }
        throw new AuthorNotfoundException("Author having this id : "+ id +" does not exist!!!");
    }

    @Override
    public void deleteAuthor(Short id) {
        authorRepo.deleteById(id);
    }

}
