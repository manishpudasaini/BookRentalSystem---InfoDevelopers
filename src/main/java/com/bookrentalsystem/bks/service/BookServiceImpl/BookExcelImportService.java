package com.bookrentalsystem.bks.service.BookServiceImpl;

import com.bookrentalsystem.bks.dto.book.BookRequest;
import com.bookrentalsystem.bks.model.Book;
import com.bookrentalsystem.bks.repo.BookRepo;
import com.bookrentalsystem.bks.service.AuthorService;
import com.bookrentalsystem.bks.service.BookService;
import com.bookrentalsystem.bks.service.CategoryService;
import com.bookrentalsystem.bks.utility.ConvertToLocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookExcelImportService {
    private final ConvertToLocalDateTime dateUtil;
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookRepo bookRepo;
    private final BookService bookService;

    public Book convertToEntity(BookRequest bookRequest){
        return Book.builder()
                .id(bookRequest.getId())
                .name(bookRequest.getName())
                .page(bookRequest.getPage())
                .isbn(bookRequest.getIsbn())
                .rating(bookRequest.getRating())
                .stock(bookRequest.getStock())
                .published_date(dateUtil.convertToDate(bookRequest.getPublished_date()))
                .category(categoryService.findCategoryById(bookRequest.getCategoryId()))
                .authors(authorService.convertToAuthorList(bookRequest.getAuthorsId()))
                .image_path(bookRequest.getImage_path())
                .deleted(false)
                .build();

    }


    public void save(MultipartFile file) throws IOException {
       List<BookRequest> bookRequests = ExcelHelper.convertToListOfBookRequest(file.getInputStream());
        bookRequests.stream().forEach(b -> bookRepo.save(convertToEntity(b)));

    }

}
