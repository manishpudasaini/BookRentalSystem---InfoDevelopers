package com.bookrentalsystem.bks.controller.author;

import com.bookrentalsystem.bks.dto.author.AuthorResponse;
import com.bookrentalsystem.bks.model.Author;
import com.bookrentalsystem.bks.service.AuthorService;
import com.bookrentalsystem.bks.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Mock
    private AuthorService authorService;
    @Mock
    private BookService bookService;

    @Autowired
    private TestEntityManager entityManager;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new AuthorController(authorService,bookService)).build();
        Author author = Author.builder()
                .id((short)11)
                .name("asgasihaj")
                .email("asklfasj@gmail.com")
                .number("8127398123")
                .deleted(false)
                .build();
    }

    @DisplayName("view author form ")
    @Test
    void ShouldViewAuthorForm() throws Exception {
        mockMvc.perform(get("/author/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("author/AuthorForm"))
                .andExpect(model().attributeExists("author"));
    }

    @Test
    @DisplayName("View Author table")
    void ShouldViewAuthorTable() throws Exception {
//        List<AuthorResponse > authorResponses = new ArrayList<>();
//        authorResponses.add(new AuthorResponse((short)11,"jhwiwh","uhqiuhd@jlsm","8698778789"));
//        authorResponses.add(new AuthorResponse((short)12,"jljlk","hkjlj@jlsm","8698782312"));

        mockMvc.perform(get("/author/table"))
                .andExpect(status().isOk())
                .andExpect(view().name("author/AuthorTable"))
                .andExpect(model().attributeExists("author"));
    }


    @Test
    @DisplayName("save the author in db ")
    void shouldSaveAuthorInDb() throws Exception {
        mockMvc.perform(post("/author/save")
                        .param("name", "authorName")
                        .param("email", "author@email.com")
                        .param("mobileNumber", "1122334455")
                )
                .andExpect(redirectedUrl("/author/table"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("Delete the author using id")
    void shouldDeleteAuthorUsingId() throws Exception {
        mockMvc.perform(delete("/author/delete/{id}",(short)11))
                .andExpect(redirectedUrl("/author/table?success"))
                .andExpect(status().is3xxRedirection());
    }
}