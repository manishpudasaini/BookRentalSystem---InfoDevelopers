package com.bookrentalsystem.bks.service.authorserviceimpl;

import com.bookrentalsystem.bks.dto.author.AuthorRequest;
import com.bookrentalsystem.bks.dto.author.AuthorResponse;
import com.bookrentalsystem.bks.exception.globalexception.AuthorCanNotBeDeletedException;
import com.bookrentalsystem.bks.model.Author;
import com.bookrentalsystem.bks.repo.AuthorRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorRepo authorRepo;


    @InjectMocks
    private AuthorServiceImpl authorService;
    private Author author;

    //this will run before every test case
    @BeforeEach
    public void setUp() {

        //given
     author = Author.builder()
                .id((short)123)
                .name("abc Author")
                .email("abcd@gmail.com")
                .number("8273891312")
                .deleted(false)
                .build();
    }

    //test success case
    @Test
    @DisplayName("Test if the author is present having the given Id")
    void shouldCheckAuthorByIdISPresentOrNot() {

        when(authorRepo.findById((short)123)).thenReturn(Optional.of(author));

        short id = 123;

       Author authorFound=  authorService.findAuthorById(id);

       assertEquals(authorFound,author);
    }

    //test failure case when author not found
    @Test
    @DisplayName("Test if the author is not present having the given ID then throws exception")
    void shouldCheckAuthorByIdISNotPresentThrowException() {

        when(authorRepo.findById((short)123)).thenReturn(Optional.empty());

        Exception exception = assertThrows(AuthorCanNotBeDeletedException.class,
                ()->{authorService.findAuthorById((short)123);});

        String expectedMessage = "Author having this does not exist!!!";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage,actualMessage);
    }

    @Test
    @DisplayName("test if the allAuthor() will give list of authors")
    void shouldReturnAllAuthors() {
//       Author author2 = Author.builder()
//                .id((short)12)
//                .name("xyzA")
//                .email("xyz@gmail.com")
//                .number("2183172831")
//                .build();

        //singletonList takes an item, and creates an immutable list containing only that item
       when(authorRepo.findAll()).thenReturn(new ArrayList<>(Collections.singleton(author)));

      List<Author> allAuthorsList = authorService.allAuthors();

        System.out.println(allAuthorsList);
      //assertThat(allAuthorsList).isNotNull();

      assertThat(allAuthorsList.get(0).getId()).isEqualTo(author.getId());

      //assertThat(allAuthorsList.size()).isEqualTo(2);
    }


    //test success case
    @Test
    @DisplayName("Junit test to add the author in db")
    void shouldAddAuthorInDb() {
        AuthorRequest authorRequest = AuthorRequest.builder()
                .id((short)123)
                .name("abc Author")
                .email("abcd@gmail.com")
                .number("8273891312")
                .build();

        when(authorRepo.save(author)).thenReturn(author);
        when(authorRepo.findByEmailAndDeletedIsTrue(author.getEmail())).thenReturn(Optional.empty());
        when(authorRepo.findByNumberAndDeletedIsTrue(author.getNumber())).thenReturn(Optional.empty());

        String result = authorService.addAuthorDb(authorRequest);

        assertEquals("save the author in db",result);

    }

    //test failure case 1st when email is deleted & present in db
    @Test
    @DisplayName("Junit test to add the author in db if it is already deleted & deleted is true")
    void shouldAddAuthorInDbWhenAuthorGivenEmailDeletedIsTrue() {
        AuthorRequest authorRequest = AuthorRequest.builder()
                .id((short)123)
                .name("abc Author")
                .email("abcd@gmail.com")
                .number("8273891312")
                .build();

        when(authorRepo.save(author)).thenReturn(author);
        when(authorRepo.findByEmailAndDeletedIsTrue(author.getEmail())).thenReturn(Optional.of(author));
        //when(authorRepo.findByNumberAndDeletedIsTrue(author.getNumber())).thenReturn(Optional.empty());

        String result = authorService.addAuthorDb(authorRequest);

        assertEquals("revive the author having same email",result);

    }

    //test failure case 2nd when phone number is deleted & present in db
    @Test
    @DisplayName("Junit test to add the author in db if it is already having same email deleted & deleted is true")
    void shouldAddAuthorInDbWhenAuthorGivenPhoneNumberDeletedIsTrue() {
        AuthorRequest authorRequest = AuthorRequest.builder()
                .id((short)123)
                .name("abc Author")
                .email("abcd@gmail.com")
                .number("8273891312")
                .build();

        when(authorRepo.save(author)).thenReturn(author);
        //when(authorRepo.findByEmailAndDeletedIsTrue(author.getEmail())).thenReturn(Optional.of(author));
        when(authorRepo.findByNumberAndDeletedIsTrue(author.getNumber())).thenReturn(Optional.of(author));

        String result = authorService.addAuthorDb(authorRequest);

        assertEquals("revive the author having same phone number",result);

    }

    @Test
    @DisplayName("test if the AuthorRequest will return Author ")
    void shouldChangeAuthorRequestToEntity() {
        AuthorRequest authorRequest = AuthorRequest.builder()
                .id((short)123)
                .name("abc Author")
                .email("abcd@gmail.com")
                .number("8273891312")
                .build();
        Author author1 = authorService.authorRequestToEntity(authorRequest);

        assertEquals(author1,author);
    }


    @Test
    @DisplayName("should delete author when id is given")
    void shouldDeleteAuthor() {

        //this is done when the function return void or does not have return type
        doAnswer(Answers.CALLS_REAL_METHODS).when(authorRepo).deleteById(any());

        assertThat(authorService.deleteAuthor((short)123)).isEqualTo("deleted successfully");

    }

    //test success case
    @Test
    @DisplayName("Should find Author using id then return AuthorResponse")
    void shouldFindAuthorResponseByIdThenReturnAuthorResponse() {
        AuthorResponse authorResponse = AuthorResponse.builder()
                .id((short)123)
                .name("abc Author")
                .email("abcd@gmail.com")
                .number("8273891312")
                .build();

        when(authorRepo.findById((short)123)).thenReturn(Optional.of(author));

        assertThat(authorService.findAuthorResponseById((short)123)).isEqualTo(authorResponse);

    }

    //test failure case when it throws exception
    @Test
    @DisplayName("Should find Author using id then Throw exception if Author not present")
    void shouldFindAuthorResponseByIdThenThrowExceptionWhenBotPresent() {
        AuthorResponse authorResponse = AuthorResponse.builder()
                .id((short)123)
                .name("abc Author")
                .email("abcd@gmail.com")
                .number("8273891312")
                .build();

        when(authorRepo.findById((short)123)).thenReturn(Optional.empty());

       Exception exception = assertThrows(AuthorCanNotBeDeletedException.class,
                ()->{authorService.findAuthorResponseById((short)123);});

       String expectedMessage  = "Author having this id does not exist!!!";
       String actualMessage = exception.getMessage();

       assertEquals(expectedMessage,actualMessage);

    }

    @Test
    @DisplayName("Update the author present in db")
    void shouldUpdateAuthorAndAddInDb() {
        AuthorRequest authorRequest = AuthorRequest.builder()
                .id((short)123)
                .name("abc Author")
                .email("abcd@gmail.com")
                .number("8273891312")
                .build();

        when(authorRepo.save(author)).thenReturn(author);

        String result = authorService.updateAuthorAdd(authorRequest);

        assertEquals(result,"updated");
    }
}