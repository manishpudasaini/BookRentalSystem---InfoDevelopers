package com.bookrentalsystem.bks.service.authorserviceimpl;

import com.bookrentalsystem.bks.dto.author.AuthorRequest;
import com.bookrentalsystem.bks.model.Author;
import com.bookrentalsystem.bks.repo.AuthorRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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


    @Test
    @DisplayName("Test if the author is present having the given Id")
    void shouldCheckAuthorByIdISPresentOrNot() {

        when(authorRepo.findById((short)123)).thenReturn(Optional.of(author));

        short id = 123;

       Author authorFound=  authorService.findAuthorById(id);

       assertEquals(authorFound,author);
    }

    @Test
    @DisplayName("test if the allAuthor() will give list of authors")
    void shouldReturnAllAuthors() {
       Author author2 = Author.builder()
                .id((short)12)
                .name("xyzA")
                .email("xyz@gmail.com")
                .number("2183172831")
                .build();

       when(authorRepo.findAll()).thenReturn(List.of(author,author2));

      List<Author> allAuthorsList = authorService.allAuthors();

        System.out.println(allAuthorsList);
      assertThat(allAuthorsList).isNotNull();

      assertThat(allAuthorsList.size()).isEqualTo(2);
    }

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

        String result = authorService.addAuthorDb(authorRequest);

        assertEquals("save the author in db",result);

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
}