package com.bookrentalsystem.bks.service.MemberServiceImpl;

import com.bookrentalsystem.bks.dto.member.MemberRequest;
import com.bookrentalsystem.bks.dto.member.MemberResponse;
import com.bookrentalsystem.bks.exception.MemberNotFoundException;
import com.bookrentalsystem.bks.model.Member;
import com.bookrentalsystem.bks.repo.MemberRepo;
import com.bookrentalsystem.bks.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepo memberRepo;

    @Override
    public MemberResponse addMember(MemberRequest memberRequest) {
        Member member = memberRepo.save(memberRequestToEtity(memberRequest));
        return entityToMemberResponse(member);
    }

    @Override
    public Member memberRequestToEtity(MemberRequest memberRequest) {
        return Member.builder()
                .name(memberRequest.getName())
                .email(memberRequest.getEmail())
                .address(memberRequest.getAddress())
                .phone(memberRequest.getPhone())
                .build();
    }

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

    @Override
    public Member findMemberById(Short id) {
       Optional<Member> singleMember =  memberRepo.findById(id);
       if(singleMember.isPresent()){
           Member member = singleMember.get();
           return member;
       }
        throw new MemberNotFoundException("Member does not exist!!!");
    }
}
