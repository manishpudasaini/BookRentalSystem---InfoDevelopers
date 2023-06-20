package com.bookrentalsystem.bks.service.returnBookImpl;

import com.bookrentalsystem.bks.enums.BookRentStatus;
import com.bookrentalsystem.bks.model.Book;
import com.bookrentalsystem.bks.model.Member;
import com.bookrentalsystem.bks.model.ReturnBookTable;
import com.bookrentalsystem.bks.model.Transaction;
import com.bookrentalsystem.bks.repo.ReturnBookRepo;
import com.bookrentalsystem.bks.service.ReturnBookTbl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReturnBookTblService implements ReturnBookTbl {
    private final ReturnBookRepo returnBookRpo;

    @Override
    public ReturnBookTable addReturnBook(Transaction transaction) {
       Member member = transaction.getMember();
        Book book = transaction.getBook();
        ReturnBookTable returnBookTable = new ReturnBookTable();
        returnBookTable.setId(transaction.getId());
        returnBookTable.setFrom_date(transaction.getFrom());
        returnBookTable.setReturnDate(transaction.getReturnDate());
        returnBookTable.setMember_name(member.getName());
        returnBookTable.setCode(transaction.getCode());
        returnBookTable.setBook_name(book.getName());
        returnBookRpo.save(returnBookTable);
        return returnBookTable;
    }

    @Override
    public List<ReturnBookTable> allReturnBooks() {
        return returnBookRpo.findAll();
    }

    @Override
    public void deleteReturnBook(Short id) {
        returnBookRpo.deleteById(id);
    }
}
