package com.bookrentalsystem.bks.service.memberserviceimpl;

import com.bookrentalsystem.bks.dto.member.MemberRequest;
import com.bookrentalsystem.bks.exception.globalexception.MemberCanNotBeDeletedException;
import com.bookrentalsystem.bks.model.Member;
import com.bookrentalsystem.bks.repo.MemberRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {
    @Mock
    private MemberRepo memberRepo;

    @InjectMocks
    private MemberServiceImpl memberService;

    private Member member;
    @BeforeEach
    void setUp() {
      member = Member.builder()
                .id((short)1)
                .name("manish")
                .address("bhadrabas")
                .email("manish@gmail.com")
                .phone("1287391823")
                .deleted(false).build();
    }

    //test success case when member are already not present in db
    @Test
    @DisplayName("should add member if not present in db")
    void shouldAddMemberInDbIfNotPresentAlready() {
        MemberRequest memberRequest = MemberRequest.builder()
                .id((short)1)
                .name("manish")
                .address("bhadrabas")
                .email("manish@gmail.com")
                .phone("1287391823").build();
        when(memberRepo.save(member)).thenReturn(member);
        when(memberRepo.findMemberByEmailAndDeletedIsTrue(member.getEmail())).thenReturn(Optional.empty());
        when(memberRepo.findByPhoneAndDeletedIsTrue(member.getPhone())).thenReturn(Optional.empty());

       String result =   memberService.addMember(memberRequest);

       assertEquals("member added to db",result);

    }

    //test failure case when member are already  present in db having same email
    @Test
    @DisplayName("should add member if not present in db")
    void shouldAddMemberInDbIfAlreadyPresentHavingSameEmail() {
        MemberRequest memberRequest = MemberRequest.builder()
                .id((short)1)
                .name("manish")
                .address("bhadrabas")
                .email("manish@gmail.com")
                .phone("1287391823").build();
        when(memberRepo.save(member)).thenReturn(member);
        when(memberRepo.findMemberByEmailAndDeletedIsTrue(member.getEmail())).thenReturn(Optional.of(member));

        String result =   memberService.addMember(memberRequest);

        assertEquals("revive the deleted member having same email",result);

    }

    //test failure case when member are already  present in db having same phone number
    @Test
    @DisplayName("should add member if not present in db")
    void shouldAddMemberInDbIfAlreadyPresentHavingSamePhoneNumber() {
        MemberRequest memberRequest = MemberRequest.builder()
                .id((short)1)
                .name("manish")
                .address("bhadrabas")
                .email("manish@gmail.com")
                .phone("1287391823").build();
        when(memberRepo.save(member)).thenReturn(member);
        when(memberRepo.findByPhoneAndDeletedIsTrue(member.getPhone())).thenReturn(Optional.of(member));
        when(memberRepo.findMemberByEmailAndDeletedIsTrue(member.getEmail())).thenReturn(Optional.empty());

        String result =   memberService.addMember(memberRequest);

        assertEquals("revive the deleted member having same phone number",result);

    }

    //test success case when member is found in db
    @Test
    @DisplayName("should check if member present using member id found")
    void shouldCheckMemberByIdWhenMemberFound() {
        when(memberRepo.findById((short)100))
                .thenReturn(Optional.of(member));

        Member memberById = memberService.findMemberById((short) 100);

        assertEquals(member.getEmail(),memberById.getEmail());
    }

    //test failure case when member is not present in db
    @Test
    @DisplayName("should check if member present using member id not found")
    void shouldCheckMemberByIdWhenMemberNotFound() {
        when(memberRepo.findById((short)100))
                .thenReturn(Optional.empty());

       Exception exception =  assertThrows(MemberCanNotBeDeletedException.class,
                ()->{memberService.findMemberById((short) 100);});

       String expectedMessage = "Member does not exist!!!";
       String actualMessage = exception.getMessage();

       assertEquals(expectedMessage,actualMessage);
    }
    @Test
    @DisplayName("should find all member present then return list of member")
    void shouldReturnAllMemberEntity() {
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

    @Test
    @DisplayName("should delete the member using ID")
    void shouldDeleteMemberByIdThenReturnMessage() {
        doAnswer(Answers.CALLS_REAL_METHODS).when(memberRepo).deleteById(any());

        String result = memberService.deleteMemberById((short)1);

        assertEquals("deleted",result);
    }

    //test success case when member is present  in database
    @Test
    @DisplayName("should find member response from the id if found")
    void shouldFindMemberResponseFromIdWhenFound() {
        when(memberRepo.findById((short)1)).thenReturn(Optional.of(member));

        assertThat(memberService.findMemberResponseFromId((short)1)).isNotNull();
    }

    //test failure case when member is not present  in database
    @Test
    @DisplayName("should find member response from the id if not found")
    void shouldFindMemberResponseFromIdWhenNotFound() {
        when(memberRepo.findById((short)1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(MemberCanNotBeDeletedException.class,
                ()->{memberService.findMemberResponseFromId((short)1);});

        String expectedMessage = "Member does not exist !!!";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage,actualMessage);
    }

    @Test
    @DisplayName("should find list of member then return MemberResponse ")
    void shouldFindAllMemberReturnListOfMemberResponse(){
        when(memberRepo.findAll()).thenReturn(new ArrayList<>(Collections.singleton(member)));
        assertThat(memberService.allMemberResponse()).isNotNull();
    }

    @Test
    @DisplayName("should change the MemberResponse dto to member entity")
    void shouldChangeMemberRequestToMemberEtity() {
        MemberRequest memberRequest = MemberRequest.builder()
                .id((short)1)
                .name("manish")
                .address("bhadrabas")
                .email("manish@gmail.com")
                .phone("1287391823").build();

       Member found =  memberService.memberRequestToEtity(memberRequest);

       assertEquals(member,found);
    }


}