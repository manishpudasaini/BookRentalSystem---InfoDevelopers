package com.bookrentalsystem.bks.controller.member;

import com.bookrentalsystem.bks.dto.category.CategoryResponse;
import com.bookrentalsystem.bks.dto.member.MemberRequest;
import com.bookrentalsystem.bks.dto.member.MemberResponse;
import com.bookrentalsystem.bks.model.Member;
import com.bookrentalsystem.bks.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/table")
    public String memberTable(Model model){
        List<Member> members =memberService.allMemberEntity();
        List<MemberResponse> memberResponses = memberService.allMemberResponseDTo(members);
        model.addAttribute("member",memberResponses);
        return "customer/CustomerTable";
    }

    @GetMapping("/form")
    public String memberForm(Model model){
        if(model.getAttribute("member") == null)
            model.addAttribute("member",new MemberRequest());
        return "customer/CustomerForm";
    }

    @PostMapping("/save")
    public String saveMember(@Valid @ModelAttribute("member") MemberRequest memberRequest,
                             BindingResult result,Model model){
        if(result.hasErrors()){
            model.addAttribute("member",memberRequest);
            return "customer/CustomerForm";
        }
        memberService.addMember(memberRequest);
        return "redirect:/member/table";
    }

    @RequestMapping("/update/{id}")
    public String memberUpdate(@PathVariable Short id, RedirectAttributes redirectAttributes){
        MemberResponse memberResponse = memberService.findMemberResponseFromId(id);
        redirectAttributes.addFlashAttribute("member",memberResponse);
        return "redirect:/member/form";
    }

    @RequestMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Short id,RedirectAttributes redirectAttributes){
        memberService.deleteMemberById(id);
        String message = "";
        redirectAttributes.addFlashAttribute("message","Category Deleted Successfully!!");
        return "redirect:/member/table?success";
    }
}
