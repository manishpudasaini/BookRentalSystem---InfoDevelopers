package com.bookrentalsystem.bks.service;

import com.bookrentalsystem.bks.dto.member.MemberRequest;
import com.bookrentalsystem.bks.dto.member.MemberResponse;
import com.bookrentalsystem.bks.model.Member;

public interface MemberService {

    MemberResponse addMember(MemberRequest memberRequest);
    Member memberRequestToEtity(MemberRequest memberRequest);
    MemberResponse entityToMemberResponse(Member member);
    Member findMemberById(Short id);
}
