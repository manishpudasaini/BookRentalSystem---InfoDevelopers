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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepo transactionRepo;
    private final GenerateRandomNumber generateRandomNumber;
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
                .code(GenerateRandomNumber.generateRandomNumber())
                .from(localDateTime.convertToDate())
                .to(localDateTime.convertToDate().plusDays(rentBookRequest.getDays()))
                .status(BookRentStatus.RENT)
                .book(bookService.findBookByid(rentBookRequest.getBookId()))
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
    public Transaction findTransactionByCode(Integer code) {
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
        String  memberName = memberService.findMemberById(transaction.getMember().getId()).getName();
        return TransactionDto.builder()
                .id(transaction.getId())
                .book_name(transaction.getBook().getName())
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


}
