package com.bookrentalsystem.bks.controller.member;

import com.bookrentalsystem.bks.dto.member.MemberRequest;
import com.bookrentalsystem.bks.dto.member.MemberResponse;
import com.bookrentalsystem.bks.model.Member;
import com.bookrentalsystem.bks.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/table")
    public String memberTable(){
        List<Member> members =memberService.allMemberEntity();
        List<MemberResponse> memberResponses = memberService.allMemberResponseDTo(members);
        return "customer/CustomerTable";
    }

    @GetMapping("/form")
    public String memberForm(Model model){
        model.addAttribute("member",new MemberRequest());
        return "customer/CustomerForm";
    }

    @GetMapping("/save")
    public String saveMember(@ModelAttribute("member") MemberRequest memberRequest){
        memberService.addMember(memberRequest);
        return "redirect:/member/table";
    }
}
