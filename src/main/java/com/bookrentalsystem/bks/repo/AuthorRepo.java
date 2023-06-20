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

}
