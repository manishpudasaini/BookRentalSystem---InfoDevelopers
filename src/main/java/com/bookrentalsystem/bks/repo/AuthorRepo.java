package com.bookrentalsystem.bks.repo;

import com.bookrentalsystem.bks.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author,Short> {

}
