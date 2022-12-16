package com.its.memberboard.controller;

import com.its.memberboard.DTO.MemberDTO;
import com.its.memberboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

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
    public String loginForm(@RequestParam(value = "redirectURL", defaultValue = "/board/list") String redirectURL,
                            Model model) {
        model.addAttribute("redirectURL", redirectURL);
        return "memberPages/member_login";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO,
                        HttpSession session,
                        Model model,
                        @RequestParam(value = "redirectURL", defaultValue = "/board/list") String redirectURL) {
        MemberDTO result = memberService.login(memberDTO);

        if (result != null && !result.getMemberEmail().equals("admin")) {
            session.setAttribute("login", result);
            model.addAttribute("login", result);
            return "redirect:" + redirectURL;
        } else if (result.getMemberEmail().equals("admin")) {
            session.setAttribute("admin", result);
            model.addAttribute("admin", result);
            return "adminPages/admin";
        } else {
            return "index";
        }
    }

    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

    @GetMapping("/member/admin")
    public String adminForm() {
        return "adminPages/admin";
    }

    @GetMapping("/member/admin/list")
    public String adminFindAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "adminPages/admin_list";
    }

    @DeleteMapping("/member/admin/delete/{id}")
    public ResponseEntity adminDelete(@PathVariable Long id) {
        memberService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
