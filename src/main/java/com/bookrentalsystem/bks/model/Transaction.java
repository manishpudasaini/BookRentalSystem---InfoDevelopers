package com.bookrentalsystem.bks.model;

import com.bookrentalsystem.bks.enums.BookRentStatus;
import com.bookrentalsystem.bks.model.auditing.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "transaction",uniqueConstraints = {
        @UniqueConstraint(name = "uk_transaction_code",columnNames = "transaction_code")
})
public class Transaction extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;
    @Column(name = "transaction_code",length = 5,nullable = false)
    private Integer code;
    @Column(name = "from_date",nullable = false)
    private LocalDateTime from;
    @Column(name = "to_date",nullable = false)
    private LocalDateTime to;
    @Column(name = "book_status",nullable = false)
    @Enumerated(EnumType.STRING)
    private BookRentStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id",foreignKey = @ForeignKey(name = "fk_transaction_bookId"))
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",foreignKey = @ForeignKey(name = "fk_transaction_memberId"))
    private Member member;




}
