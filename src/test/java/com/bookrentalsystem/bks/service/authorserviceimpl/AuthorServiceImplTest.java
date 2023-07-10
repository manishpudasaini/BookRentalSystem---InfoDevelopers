package com.bookrentalsystem.bks.service.authorserviceimpl;

import com.bookrentalsystem.bks.dto.author.AuthorRequest;
import com.bookrentalsystem.bks.dto.author.AuthorResponse;
import com.bookrentalsystem.bks.model.Author;
import com.bookrentalsystem.bks.repo.AuthorRepo;
import com.bookrentalsystem.bks.service.AuthorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
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
                .build();
    }


    @Test
    @DisplayName("Test if the author is present having the given Id")
    void whenCheckAuthorById_IS_PresentOrNot() {

        when(authorRepo.findById((short)123)).thenReturn(Optional.of(author));

        short id = 123;

       Author authorFound=  authorService.findAuthorById(id);

       assertEquals(authorFound,author);
    }

    @Test
    @DisplayName("test if the allAuthor() will give list of authors")
    void allAuthors() {
       Author author2 = Author.builder()
                .id((short)12)
                .name("xyzA")
                .email("xyz@gmail.com")
                .number("2183172831")
                .build();

       when(authorRepo.findAll()).thenReturn(List.of(author,author2));

      List<Author> allAuthorsList = authorService.allAuthors();

      assertThat(allAuthorsList).isNotNull();

      assertThat(allAuthorsList.size()).isEqualTo(2);
    }

    @Test
    void addAuthorDb() {
        AuthorRequest authorRequest = AuthorRequest.builder()
                .id((short)11)
                .name("asfjasf")
                .email("asfjasf@gmail.com")
                .number("8123719283")
                .build();

        when(authorRepo.save(author)).thenReturn(author);

        authorService.addAuthorDb(authorRequest);

        assertEquals(authorRequest.getName(),"asfjasf");

    }
}