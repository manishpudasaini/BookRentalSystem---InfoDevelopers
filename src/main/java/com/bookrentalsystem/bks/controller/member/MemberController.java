package com.bookrentalsystem.bks.controller.member;

import com.bookrentalsystem.bks.dto.member.MemberRequest;
import com.bookrentalsystem.bks.dto.member.MemberResponse;
import com.bookrentalsystem.bks.enums.BookRentStatus;
import com.bookrentalsystem.bks.exception.globalexception.MemberCanNotBeDeletedException;
import com.bookrentalsystem.bks.model.Member;
import com.bookrentalsystem.bks.model.Transaction;
import com.bookrentalsystem.bks.service.MemberService;
import com.bookrentalsystem.bks.service.TransactionService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final TransactionService transactionService;

    //member list
    @PreAuthorize("hasAuthority('LIBRARIAN')")
    @GetMapping("/table")
    public String memberTable(Model model){
        List<Member> members =memberService.allMemberEntity();
        List<MemberResponse> memberResponses = memberService.allMemberResponseDTo(members);
        model.addAttribute("member",memberResponses);
        return "customer/CustomerTable";
    }

    //add member form
    @PreAuthorize("hasAuthority('LIBRARIAN')")
    @GetMapping("/form")
    public String memberForm(Model model){
        if(model.getAttribute("member") == null)
            model.addAttribute("member",new MemberRequest());
        return "customer/CustomerForm";
    }

    //save the member - validation
    @PostMapping("/save")
    public String saveMember(@Valid @ModelAttribute("member") MemberRequest memberRequest,
                             BindingResult result,Model model,RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            model.addAttribute("member",memberRequest);
            return "customer/CustomerForm";
        }

        String message= memberService.addMember(memberRequest);

        if(message!=null){
            redirectAttributes.addFlashAttribute("message","Member added");
           return  "redirect:/member/table";
        }
        return "message";
    }

    @RequestMapping("/update/{id}")
    public String memberUpdate(@PathVariable Short id, Model model){
        MemberResponse memberResponse = memberService.findMemberResponseFromId(id);
        model.addAttribute("member",memberResponse);

        return "/customer/CustomerUpdate";
    }

//    @PostMapping("/update/save")
//    public String saveUpdateMember(@Valid @ModelAttribute("member") MemberRequest memberRequest,
//                             BindingResult result,Model model){
//        if(result.hasErrors()){
//            model.addAttribute("member",memberRequest);
//            return "/customer/CustomerUpdate";
//        }
//
//        memberService.addUpdateMember(memberRequest);
//        return "redirect:/member/table";
//    }

    //delete the member - soft delete
    @RequestMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Short id,RedirectAttributes redirectAttributes){
       List<Transaction> transaction =  transactionService.allTransactionEntity();
       List<Transaction> filterTransaction =transaction.stream().filter(t -> t.getMember().getId() == id)
                       .filter(t -> t.getStatus().equals(BookRentStatus.RENT)).collect(Collectors.toList());

       if(filterTransaction.size() == 0){
           memberService.deleteMemberById(id);
       }else {
           throw new MemberCanNotBeDeletedException("Cannot delete this member");
       }

        String message = "";
        redirectAttributes.addFlashAttribute("message","Member Deleted Successfully!!");
        return "redirect:/member/table?success";
    }
}
