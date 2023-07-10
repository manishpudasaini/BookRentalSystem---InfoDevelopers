package com.bookrentalsystem.bks.service.memberserviceimpl;

import com.bookrentalsystem.bks.model.Member;
import com.bookrentalsystem.bks.repo.MemberRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {
    @Mock
    private MemberRepo memberRepo;

    @InjectMocks
    private MemberServiceImpl memberService;

    @Test
    void checkMemberById() {

        when(memberRepo.findById((short)100))
                .thenReturn(Optional.of(new Member((short)100,"manish","manish@gmail.com","bhadrabas","2731981281",false)));

        Member memberById = memberService.findMemberById((short) 100);

        assertEquals("manish@gmail.com",memberById.getEmail());

    }

    @Test
    void allMemberEntity() {
        List<Member> members = new ArrayList<>();
        members.add(new Member((short)100,"manish","manish@gmail.com","bhadrabas","83891282109",false));
        members.add(new Member((short)100,"ronaldo","ronaldo@gmail.com","jorpati","91280391821",false));

        //mock the findall() method as memberRepo
        when(memberRepo.findAll()).thenReturn(members);

        //calling the service method
       List<Member> result =  memberService.allMemberEntity();

        //verify the result
        assertEquals(2,result.size());
        assertEquals("manish",result.get(0).getName());
        assertEquals("ronaldo",result.get(1).getName());

    }
}