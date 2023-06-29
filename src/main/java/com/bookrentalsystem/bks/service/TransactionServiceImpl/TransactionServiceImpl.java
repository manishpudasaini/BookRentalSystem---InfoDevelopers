package com.bookrentalsystem.bks.service.TransactionServiceImpl;

import com.bookrentalsystem.bks.dto.transaction.TransactionDto;
import com.bookrentalsystem.bks.dto.transaction.rentBook.RentBookRequest;
import com.bookrentalsystem.bks.dto.transaction.rentBook.RentBookResponse;
import com.bookrentalsystem.bks.dto.transaction.returnBook.ReturnBookRequest;
import com.bookrentalsystem.bks.enums.BookRentStatus;
import com.bookrentalsystem.bks.exception.globalException.CodeNotFoundException;
import com.bookrentalsystem.bks.model.Book;
import com.bookrentalsystem.bks.model.Member;
import com.bookrentalsystem.bks.model.Transaction;
import com.bookrentalsystem.bks.repo.TransactionRepo;
import com.bookrentalsystem.bks.service.BookService;
import com.bookrentalsystem.bks.service.MemberService;
import com.bookrentalsystem.bks.service.TransactionService;
import com.bookrentalsystem.bks.utility.ConvertToLocalDateTime;
import com.bookrentalsystem.bks.utility.GenerateRandomNumber;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepo transactionRepo;
    private final ConvertToLocalDateTime localDateTime;
    private final BookService bookService;
    private final MemberService memberService;


    //method to save or rent the book
    public RentBookResponse rentABook(RentBookRequest rentBookRequest){
        Transaction transaction = rentBookRequestToTransacrion(rentBookRequest);
        transactionRepo.save(transaction);
        return transactionToRentBookResponse(transaction);
    }

    //method to convert the rentBookRequest to transaction
    public Transaction rentBookRequestToTransacrion(RentBookRequest rentBookRequest){
       Book singleBook  = bookService.findBookByid(rentBookRequest.getBookId());
       singleBook.setStock(singleBook.getStock() - 1);
        bookService.saveBook(singleBook);
        return Transaction.builder()
                .code(GenerateRandomNumber.generateRandomNumber().toString())
                .from(localDateTime.convertToDate())
                .to(localDateTime.convertToDate().plusDays(rentBookRequest.getDays()))
                .status(BookRentStatus.RENT)
                .book(singleBook)
                .member(memberService.findMemberById(rentBookRequest.getMemberId()))
                .build();
    }

    //method to convert transaction to RentBookResponse
    public RentBookResponse transactionToRentBookResponse(Transaction transaction){
        return RentBookResponse.builder()
                .id(transaction.getId())
                .code(transaction.getCode())
                .from(transaction.getFrom())
                .to(transaction.getTo())
                .status(transaction.getStatus())
                .book(transaction.getBook())
                .member(transaction.getMember())
                .build();
    }

    //method to find all the rent Book list
    @Override
    public List<RentBookResponse> allRentBooks() {
       List<Transaction> transactions = transactionRepo.findAll();
        return transactions.stream()
                .map(this::transactionToRentBookResponse).collect(Collectors.toList());
    }


    //this function is used to find the transaction from code
    @Override
    public Transaction findTransactionByCode(String code) {
        Optional<Transaction> singleTransaction= transactionRepo.findByCode(code);
        if(singleTransaction.isPresent()){
            return singleTransaction.get();
        }
        throw new CodeNotFoundException("Transaction code does not exist");
    }

    //method to convert transactionToReturnresponse
    @Override
    public ReturnBookRequest transactionToReturnBook(Transaction transaction) {
        Member member =  transaction.getMember();

        return ReturnBookRequest.builder()
                .code(transaction.getCode())
                .from(transaction.getFrom().toString())
                .member_name(member.getName())
                .build();
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {

        return transactionRepo.save(transaction);
    }

    //convert transaction to transactionDto
    @Override
    public TransactionDto transactionToTransactionDto(Transaction transaction) {
        String memberName;
        String bookName ;
        try{
            memberName = transaction.getMember().getName();
             bookName = transaction.getBook().getName();
        }catch (EntityNotFoundException e){
            memberName = null;
            bookName = null;
            System.out.println(e);
        }

        return TransactionDto.builder()
                .id(transaction.getId())
                .book_name(bookName)
                .code(transaction.getCode())
                .status(transaction.getStatus())
                .member_name(memberName)
                .build();
    }

    @Override
    public List<TransactionDto> allTransaction() {
       List<Transaction> transactions= transactionRepo.findAll();
        return transactions.stream()
                .map(this::transactionToTransactionDto).collect(Collectors.toList());
    }

    @Override
    public List<Transaction> allTransactionEntity() {
        List<Transaction> transactions = transactionRepo.findAll();
        return transactions
               .stream().filter(t -> t.getStatus().equals(BookRentStatus.RENT)).collect(Collectors.toList());
    }


    @Override
    public ByteArrayInputStream downloadHistoryInExcel() throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        XSSFSheet sheet = workbook.createSheet("Transaction_History");


        List<Transaction> allTransactionHistory = transactionRepo.findAll();

        String[] header = {"ID", "CODE", "Date Of Rent", "Date of Return"
                ,"Status","Return Date","Book name","Member name"};

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 13);
        headerFont.setColor(IndexedColors.BLUE.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Create cells
        for(int i = 0; i < header.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(header[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowNUm = 1;

        for(Transaction transaction:allTransactionHistory){
            Row row = sheet.createRow(rowNUm++);
            createCell(transaction,row,workbook);
        }

        //FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\eziom\\OneDrive\\Desktop\\excelFiles\\TransactionHistory.xlsx");
//        workbook.write(fileOutputStream);
//        fileOutputStream.close();

        workbook.write(byteArrayOutputStream);
        byteArrayOutputStream.close();



//        return "Transaction history saved in excel";
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

     private void createCell(Transaction transaction, Row row,
                             XSSFWorkbook workbook) throws IOException // creating cells for each row
    {

        /* CreationHelper helps us create instances of various things like DataFormat,
           Hyperlink  in a format (HSSF, XSSF)*/
        CreationHelper createHelper = workbook.getCreationHelper();

//        CellStyle dateCellStyle = workbook.createCellStyle();
//        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("MMM/dd/yyyy"));


        Cell cell = row.createCell(0);
        cell.setCellValue(transaction.getId());

        cell = row.createCell(1);
        cell.setCellValue(transaction.getCode());

        cell = row.createCell(2);
        cell.setCellValue(transaction.getFrom().format(DateTimeFormatter.ofPattern("MMM/dd/yyyy")));

        cell = row.createCell(3);
        cell.setCellValue(transaction.getTo().format(DateTimeFormatter.ofPattern("MMM/dd/yyyy")));


        cell = row.createCell(4);
        cell.setCellValue(transaction.getStatus().toString());

        cell = row.createCell(5);
        if(transaction.getReturnDate() != null){
            cell.setCellValue(transaction.getReturnDate().format(DateTimeFormatter.ofPattern("MMM/dd/yyyy")));
        }else {
            cell.setCellValue("");
        }

        cell = row.createCell(6);
        cell.setCellValue(transaction.getBook().getName());

        cell = row.createCell(7);
        cell.setCellValue(transaction.getMember().getName());


    }


}
