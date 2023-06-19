package com.bookrentalsystem.bks.service;

import com.bookrentalsystem.bks.dto.member.MemberRequest;
import com.bookrentalsystem.bks.dto.member.MemberResponse;
import com.bookrentalsystem.bks.model.Member;

import java.util.List;

public interface MemberService {

    MemberResponse addMember(MemberRequest memberRequest);
    Member memberRequestToEtity(MemberRequest memberRequest);
    MemberResponse entityToMemberResponse(Member member);
    Member findMemberById(Short id);
    MemberResponse findMemberResponseFromId(Short id);
    List<Member > allMemberEntity();
    List<MemberResponse> allMemberResponse();
    List<MemberResponse> allMemberResponseDTo(List<Member> members);
    void deleteMemberById(short id);

}
