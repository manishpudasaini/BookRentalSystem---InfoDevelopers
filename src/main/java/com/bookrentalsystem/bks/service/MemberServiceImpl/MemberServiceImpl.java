package com.bookrentalsystem.bks.service.MemberServiceImpl;

import com.bookrentalsystem.bks.dto.member.MemberRequest;
import com.bookrentalsystem.bks.dto.member.MemberResponse;
import com.bookrentalsystem.bks.exception.globalException.MemberCanNotBeDeletedException;
import com.bookrentalsystem.bks.model.Member;
import com.bookrentalsystem.bks.repo.MemberRepo;
import com.bookrentalsystem.bks.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepo memberRepo;

    //method to save members
    @Override
    public String addMember(MemberRequest memberRequest) {
       Optional<Member> memberDeleteFalse =  memberRepo.findMemberByEmailAndDeletedIsFalse(memberRequest.getEmail());
       Optional<Member> memberDeleteTrue = memberRepo.findMemberByEmailAndDeletedIsTrue(memberRequest.getEmail());
       if(memberDeleteTrue.isPresent()){
           Member deleteMember = memberDeleteTrue.get();
           if(memberRequest.getEmail().equals(deleteMember.getEmail()) ||
                   memberRequest.getPhone().equals(deleteMember.getPhone())){
               return "Member having same information already exist!!";
           }
       }

       if(memberDeleteFalse.isPresent()){
           Member notDeleteMember =memberDeleteFalse.get();
           if(memberRequest.getEmail().equals(notDeleteMember.getEmail()) ||
                   memberRequest.getPhone().equals(notDeleteMember.getPhone())){
               return "Member having same information already exist!!";
           }
       }
        memberRepo.save(memberRequestToEtity(memberRequest));
        return "added";
    }

    //this method is used to update the member
    @Override
    public String addUpdateMember(MemberRequest memberRequest) {
       Member member = memberRequestToEtity(memberRequest);
        memberRepo.save(member);
        return "updated";
    }

    //convert memberRequest to Entity
    @Override
    public Member memberRequestToEtity(MemberRequest memberRequest) {
        return Member.builder()
                .id(memberRequest.getId())
                .name(memberRequest.getName())
                .email(memberRequest.getEmail())
                .address(memberRequest.getAddress())
                .phone(memberRequest.getPhone())
                .deleted(false)
                .build();
    }

    //convert entity to memberResponse
    @Override
    public MemberResponse entityToMemberResponse(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .address(member.getAddress())
                .phone(member.getPhone())
                .build();
    }

    //find member by its id
    @Override
    public Member findMemberById(Short id) {
       Optional<Member> singleMember =  memberRepo.findById(id);
       if(singleMember.isPresent()){
           return singleMember.get();
       }
        throw new MemberCanNotBeDeletedException("Member does not exist!!!");
    }

    //find memberResponse Dto from ID
    @Override
    public MemberResponse findMemberResponseFromId(Short id) {
        Optional<Member> singleMember = memberRepo.findById(id);
        if(singleMember.isPresent()){
            Member member = singleMember.get();
            return entityToMemberResponse(member);
        }
        throw new MemberCanNotBeDeletedException("Member does not exist !!!");
    }

    //List of  = Members
    @Override
    public List<Member> allMemberEntity() {
        return memberRepo.findAll();
    }


    //List of = MemberResponse
    @Override
    public List<MemberResponse> allMemberResponse() {
       List<Member> members = memberRepo.findAll();
        return members.stream().map(this::entityToMemberResponse).collect(Collectors.toList());
    }

    @Override
    public List<MemberResponse> allMemberResponseDTo(List<Member> members) {
        return members.stream()
                 .map(this::entityToMemberResponse).collect(Collectors.toList());
    }

    //delete member from its ID
    @Override
    public void deleteMemberById(short id) {
        memberRepo.deleteById(id);
    }
}
