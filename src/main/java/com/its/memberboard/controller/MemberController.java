package com.its.memberboard.controller;

import com.its.memberboard.DTO.MemberDTO;
import com.its.memberboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member/save")
    public String saveForm() {
        return "memberPages/member_save";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        memberService.save(memberDTO);
        return "memberPages/member_login";
    }

    @PostMapping("/member/dup-check")
    public ResponseEntity emailCheck(@RequestParam("inputEmail") String memberEmail) {
        String result = memberService.duplicateCheck(memberEmail);

        if (result != null) {
            return new ResponseEntity<>("사용가능", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("사용불가", HttpStatus.CONFLICT);
        }

    }

    @GetMapping("/member/login")
    public String loginForm() {
        return "memberPages/member_login";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO,
                        HttpSession session,
                        Model model) {
        MemberDTO result = memberService.login(memberDTO);

        if (result != null) {
            model.addAttribute("login", result);
            session.setAttribute("login", result);
            return "boardPages/board_list";
        } else {
            return "index";
        }


    }
}
