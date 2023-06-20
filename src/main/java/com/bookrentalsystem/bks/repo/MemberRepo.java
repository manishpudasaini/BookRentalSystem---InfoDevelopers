package com.bookrentalsystem.bks.repo;

import com.bookrentalsystem.bks.model.Category;
import com.bookrentalsystem.bks.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepo extends JpaRepository<Member,Short> {
    Optional<Member> findMemberByEmailAndDeletedIsFalse(String name);
    @Query(nativeQuery = true,value = "select * from members where members.email_address=?1 and members.deleted=true")
    Optional<Member> findMemberByEmailAndDeletedIsTrue(String name);
}
