package tobyspring.splearn.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tobyspring.splearn.application.provided.MemberRegister;
import tobyspring.splearn.application.required.EmailSender;
import tobyspring.splearn.application.required.MemberRepository;
import tobyspring.splearn.domain.Member;
import tobyspring.splearn.domain.MemberRegisterRequest;
import tobyspring.splearn.domain.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberRegister {
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {
        var member = Member.register(registerRequest, passwordEncoder);
        memberRepository.save(member);
        emailSender.send(member.getEmail(), "Welcome!", "Thank you for registering.");
        return member;
    }
}
