package com.bookrentalsystem.bks.repo;

import com.bookrentalsystem.bks.model.login.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Short> {
    Optional<User> findUserByEmail(String email);


}
