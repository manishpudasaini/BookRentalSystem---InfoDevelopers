package com.bookrentalsystem.bks.repo;

import com.bookrentalsystem.bks.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepoTest {

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                .id((short)11)
                .name("Toni Kroos")
                .email("toni@gmail.com")
                .phone("8127318922")
                .address("germany")
                .deleted(true).build();

        //persist the data
        testEntityManager.merge(member);
    }

    @Test
    void whenFindMemberByEmail_AND_DeletedIsTrue_ReturnMember() {
     Optional<Member> member = memberRepo.findMemberByEmailAndDeletedIsTrue("toni@gmail.com");

     assertThat(member.isPresent());
     assertEquals(member.get().getName(),"Toni Kroos");
    }


    @Test
    void whenFindMemberByPhone_AND_DeletedIsTrue_ReturnMember() {
       Optional<Member> member = memberRepo.findByPhoneAndDeletedIsTrue("8127318922");
       assertThat(member.isPresent());
    }
}