package com.bookrentalsystem.bks.controller.member;

import com.bookrentalsystem.bks.RestApiService.RestMemberService;
import com.bookrentalsystem.bks.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestMemberController {

    private final RestMemberService memberService;

    @PostMapping("/member/add")
    @PreAuthorize("hasAuthority('LIBRARIAN')")
    public ResponseEntity<Member> addMember(@RequestBody Member member){
        return new ResponseEntity<>(memberService.addMember(member), HttpStatus.OK);
    }
}
