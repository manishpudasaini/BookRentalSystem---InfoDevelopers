package com.bookrentalsystem.bks.service.BookServiceImpl;

import com.bookrentalsystem.bks.dto.book.BookRequest;
import com.bookrentalsystem.bks.model.Book;
import com.bookrentalsystem.bks.repo.BookRepo;
import com.bookrentalsystem.bks.service.AuthorService;
import com.bookrentalsystem.bks.service.BookService;
import com.bookrentalsystem.bks.service.CategoryService;
import com.bookrentalsystem.bks.utility.ConvertToLocalDateTime;
import com.bookrentalsystem.bks.utility.Fileutils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookExcelImportService {
    private final ConvertToLocalDateTime dateUtil;
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookRepo bookRepo;
    private final BookService bookService;

    public Book convertToEntity(BookRequest bookRequest)   {


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
                .image_path(saveFile(bookRequest.getImage_path()))
                .deleted(false)
                .build();

    }


    public void save(MultipartFile file) throws IOException {

       List<BookRequest> bookRequests = ExcelHelper.convertToListOfBookRequest(file.getInputStream());
       for(BookRequest b:bookRequests){
          Optional<Book> bookDeletedFalse =  bookRepo.findByNameAndDeletedIsFalse(b.getName());
          Optional<Book> bookDeletedTrue = bookRepo.findByNameAndDeletedIsTrue(b.getName());

           if(bookDeletedFalse.isEmpty()){
               bookRepo.save(convertToEntity(b));
           }

           if (bookDeletedTrue.isPresent()) {
              Book book = bookDeletedTrue.get();
               book.setDeleted(false);
               book.setPage(b.getPage());
               book.setIsbn(b.getIsbn());
               book.setStock(b.getStock());
               book.setRating(b.getRating());
               book.setImage_path(b.getImage_path());

               bookRepo.save(book);
           }
       }
       // bookRequests.stream().forEach(b -> bookRepo.save(convertToEntity(b)));

    }

    public String saveFile(String imagePath) {
        File sourceFile = new File(imagePath);

        String fileName = imagePath.substring(imagePath.lastIndexOf("\\"));
        String destinationFilePath= "C:\\Users\\eziom\\Desktop\\Book_Rental_System\\"+fileName;
        File destinationLocation = new File(destinationFilePath);
        try {
            FileUtils.copyFile(sourceFile,destinationLocation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return destinationFilePath;
    }
}
