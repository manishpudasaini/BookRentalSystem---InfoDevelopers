package com.bookrentalsystem.bks.repo;

import com.bookrentalsystem.bks.model.Author;
import com.bookrentalsystem.bks.model.Category;
import com.bookrentalsystem.bks.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepo extends JpaRepository<Member,Short> {
    Optional<Member> findMemberByEmailAndDeletedIsFalse(String name);
    @Query(nativeQuery = true,value = "select * from members where email_address=?1 and deleted=true")
    Optional<Member> findMemberByEmailAndDeletedIsTrue(String name);

    Optional<Member> findByPhoneAndDeletedIsFalse(String number);

    @Query(nativeQuery = true,value = "select * from members where phone_number=?1 and deleted=true")
    Optional<Member> findByPhoneAndDeletedIsTrue(String number);
}
