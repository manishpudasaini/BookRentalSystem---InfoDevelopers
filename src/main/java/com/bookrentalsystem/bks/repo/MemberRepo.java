package com.bookrentalsystem.bks.repo;

import com.bookrentalsystem.bks.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<Member,Short> {
}
