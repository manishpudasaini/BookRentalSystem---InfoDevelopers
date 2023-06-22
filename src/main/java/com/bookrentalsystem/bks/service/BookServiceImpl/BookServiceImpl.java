package com.bookrentalsystem.bks.service.BookServiceImpl;

import com.bookrentalsystem.bks.dto.book.BookRequest;
import com.bookrentalsystem.bks.dto.book.BookResponse;
import com.bookrentalsystem.bks.exception.globalException.BookCanNotBeDeletedException;
import com.bookrentalsystem.bks.model.Author;
import com.bookrentalsystem.bks.model.Book;
import com.bookrentalsystem.bks.repo.BookRepo;
import com.bookrentalsystem.bks.service.AuthorService;
import com.bookrentalsystem.bks.service.BookService;
import com.bookrentalsystem.bks.service.CategoryService;
import com.bookrentalsystem.bks.utility.ConvertToLocalDateTime;
import com.bookrentalsystem.bks.utility.Fileutils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepo bookRepo;
    private final ConvertToLocalDateTime dateUtil;
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final Fileutils fileutils;

    public BookResponse addBook(BookRequest bookRequest) throws IOException {
        String imagePath = null;
        if (bookRequest.getId() != null && bookRequest.getImageFile().isEmpty()) {
            Book book = bookRepo.findById(bookRequest.getId()).orElseThrow();
            imagePath = book.getImage_path();
        } else {
            imagePath = fileutils.saveMultipartFile(bookRequest.getImageFile());

        }
//        else if (bookRequest.getId() != null && bookRequest.getImageFile() != null) {

        Book book = Book.builder()
                .id(bookRequest.getId())
                .name(bookRequest.getName())
                .page(bookRequest.getPage())
                .isbn(bookRequest.getIsbn())
                .rating(bookRequest.getRating())
                .stock(bookRequest.getStock())
                .published_date(dateUtil.convertToDate(bookRequest.getPublished_date()))
                .category(categoryService.findCategoryById(bookRequest.getCategoryId()))
                .authors(authorService.convertToAuthorList(bookRequest.getAuthorsId()))
                .image_path(imagePath)
                .deleted(false)
                .build();
        bookRepo.save(book);

        List<Short> authorIds = book.getAuthors().stream().map(Author::getId).collect(Collectors.toList());

        return BookResponse.builder()
                .id(book.getId())
                .name(book.getName())
                .page(book.getPage())
                .isbn(book.getIsbn())
                .rating(book.getRating())
                .stock(book.getStock())
                .published_date(book.getPublished_date().toString())
                .image_path(book.getImage_path())
                .category(book.getCategory().getId())
                .authorsId(authorIds)
                .build();
    }

    @Override
    public Book findBookByid(Short id) {
        Optional<Book> singleBook = bookRepo.findById(id);
        if (singleBook.isPresent()) {
            return singleBook.get();
        }
        throw new BookCanNotBeDeletedException("Book does not exist!!!");
    }

    //method which return List of bookResponse dto
    @Override
    public List<BookResponse> allBooks() {
        List<Book> allBooks = bookRepo.findAll();
        return entityListToBookResponse(allBooks);
    }

    //method to delete book
    @Override
    public void deleteBook(Short id) {
        bookRepo.deleteById(id);
    }


    //method used to convert entity Book to BookResponse dto
    public BookResponse entityTBookResponse(Book book) {
        List<Short> authorIds = book.getAuthors().stream().map(Author::getId).collect(Collectors.toList());
        return BookResponse.builder()
                .id(book.getId())
                .name(book.getName())
                .page(book.getPage())
                .isbn(book.getIsbn())
                .rating(book.getRating())
                .stock(book.getStock())
                .published_date(book.getPublished_date().toString())
                .image_path(book.getImage_path())
                .category(book.getCategory().getId())
                .categoryId(book.getCategory().getId())
                .authorsId(authorIds)
                .build();
    }

    public List<BookResponse> entityListToBookResponse(List<Book> books) {

        return books
                .stream().map(this::entityTBookResponse).collect(Collectors.toList());
    }

    // this function is used to find book by its id & return Bookreponse
    @Override
    public BookResponse viewBookId(Short id) throws IOException {
        Optional<Book> singleBook = bookRepo.findById(id);

        if (singleBook.isPresent()) {
            Book book = singleBook.get();
            List<Short> authorIds = book.getAuthors().stream().map(Author::getId).collect(Collectors.toList());


            return BookResponse.builder()
                    .id(book.getId())
                    .name(book.getName())
                    .page(book.getPage())
                    .isbn(book.getIsbn())
                    .rating(book.getRating())
                    .stock(book.getStock())
                    .published_date(book.getPublished_date().toString())
                    .image_path(fileutils.getBase64FormFilePath(book.getImage_path()))
                    .category(book.getCategory().getId())
                    .authorsId(authorIds)
                    .build();
        }
        throw new BookCanNotBeDeletedException("Book does not exist!!!");
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepo.save(book);
    }
}
