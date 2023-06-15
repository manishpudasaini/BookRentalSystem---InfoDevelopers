package com.bookrentalsystem.bks.service.TransactionServiceImpl;

import com.bookrentalsystem.bks.dto.transaction.rentBook.RentBookRequest;
import com.bookrentalsystem.bks.dto.transaction.rentBook.RentBookResponse;
import com.bookrentalsystem.bks.enums.BookRentStatus;
import com.bookrentalsystem.bks.model.Transaction;
import com.bookrentalsystem.bks.repo.TransactionRepo;
import com.bookrentalsystem.bks.service.BookService;
import com.bookrentalsystem.bks.service.MemberService;
import com.bookrentalsystem.bks.service.TransactionService;
import com.bookrentalsystem.bks.utility.ConvertToLocalDateTime;
import com.bookrentalsystem.bks.utility.GenerateRandomNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepo transactionRepo;
    private final GenerateRandomNumber generateRandomNumber;
    private final ConvertToLocalDateTime localDateTime;
    private final BookService bookService;
    private final MemberService memberService;


    public RentBookResponse rentABook(RentBookRequest rentBookRequest){
        Transaction transaction = rentBookRequestToTransacrion(rentBookRequest);
        transactionRepo.save(transaction);
        return transactionToRentBookResponse(transaction);
    }

    public Transaction rentBookRequestToTransacrion(RentBookRequest rentBookRequest){
        return Transaction.builder()
                .code(generateRandomNumber.generateRandomNumber())
                .from(localDateTime.convertToDate())
                .to(localDateTime.convertToDate().plusDays(rentBookRequest.getDays()))
                .status(BookRentStatus.RENT)
                .book(bookService.findBookByid(rentBookRequest.getBookId()))
                .member(memberService.findMemberById(rentBookRequest.getMemberId()))
                .build();
    }

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

}
