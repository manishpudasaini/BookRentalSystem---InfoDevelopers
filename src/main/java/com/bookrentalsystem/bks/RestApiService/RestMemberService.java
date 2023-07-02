package com.bookrentalsystem.bks.RestApiService;

import com.bookrentalsystem.bks.model.Member;
import com.bookrentalsystem.bks.repo.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestMemberService {
    private final MemberRepo memberRepo;

    public Member addMember(Member member){
       return memberRepo.save(member);
    }

}
