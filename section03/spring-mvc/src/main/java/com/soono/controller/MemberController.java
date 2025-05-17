package com.soono.controller;

import com.soono.model.Member;
import com.soono.model.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/new-form")
    public String showForm() {
        return "member/form";
    }

    @PostMapping("/save")
    public String saveMember(Member member, Model model) {
        memberRepository.save(member);
        model.addAttribute("member", member);

        return "member/result";
    }

    @GetMapping("/list")
    public String listMembers(Model model) {
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);

        return "member/list";
    }
}
