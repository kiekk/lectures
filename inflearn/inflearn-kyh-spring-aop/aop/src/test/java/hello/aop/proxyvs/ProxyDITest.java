package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.impl.MemberServiceImpl;
import hello.aop.proxyvs.code.ProxyDIAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
//@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"})   // JDK 동적 프록시
@SpringBootTest(properties = {"spring.aop.proxy-target-class=true"})   // CGLIB 프록시
@Import(ProxyDIAspect.class)
public class ProxyDITest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberServiceImpl memberServiceImpl;

    @Test
    void go() {
        log.info("memberService class={}", memberService.getClass());
        /*
        JDK 동적 프록시 방식 사용시 에러 발생
        CGLIB 사용시 성공

        이유:
        JDK 동적 프록시 방식은 interface 기반으로 생성되기 떄문에
        MemberService 타입으로 프록시 객체가 생성됨

        CGLIB 프록시 방식은 구체 클래스 기반으로 생성되기 떄문에
        MemberServiceImpl 객체로 프록시 객체 생성 가능
         */
        log.info("memberServiceImpl class={}", memberServiceImpl.getClass());
        memberServiceImpl.hello("hello");
    }

}
