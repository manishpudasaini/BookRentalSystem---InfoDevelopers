package com.bookrentalsystem.bks.repo;

import com.bookrentalsystem.bks.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepo extends JpaRepository<Author,Short> {
    Optional<Author> findByEmailAndDeletedIsFalse(String name);
    @Query(nativeQuery = true,value = "select * from author where author.email=?1 and author.deleted=true")
    Optional<Author> findByEmailAndDeletedIsTrue(String email);

    Optional<Author> findByNumberAndDeletedIsFalse(String number);

    @Query(nativeQuery = true,value = "select * from author where author.phone_number=?1 and author.deleted=true")
    Optional<Author> findByNumberAndDeletedIsTrue(String number);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM Author a WHERE a.email = ?1")
    Boolean findUsingEmail(String email);

}
