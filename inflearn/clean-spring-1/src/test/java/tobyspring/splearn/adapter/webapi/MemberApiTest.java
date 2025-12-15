package tobyspring.splearn.adapter.webapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import tobyspring.splearn.adapter.webapi.dto.MemberRegisterResponse;
import tobyspring.splearn.application.member.provided.MemberRegister;
import tobyspring.splearn.application.member.required.MemberRepository;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberFixture;
import tobyspring.splearn.domain.member.MemberRegisterRequest;
import tobyspring.splearn.domain.member.MemberStatus;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static tobyspring.splearn.AssertThatUtils.equalsTo;
import static tobyspring.splearn.AssertThatUtils.notNull;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@RequiredArgsConstructor
public class MemberApiTest {
    final MockMvcTester mvcTester;
    final ObjectMapper objectMapper;
    final MemberRepository memberRepository;
    final MemberRegister memberRegister;

    @Test
    void register() throws JsonProcessingException, UnsupportedEncodingException {
        MemberRegisterRequest memberRegisterRequest = MemberFixture.createMemberRegisterRequest();
        String registerJson = objectMapper.writeValueAsString(memberRegisterRequest);

        MvcTestResult result = mvcTester.post().uri("/api/members").contentType(MediaType.APPLICATION_JSON)
                .content(registerJson).exchange();

        assertThat(result)
                .hasStatusOk()
                .bodyJson()
                .hasPathSatisfying("$.memberId", notNull())
                .hasPathSatisfying("$.email", equalsTo(memberRegisterRequest));

        MemberRegisterResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), MemberRegisterResponse.class);
        Member member = memberRepository.findById(response.memberId()).orElseThrow();

        assertThat(member.getEmail().value()).isEqualTo(memberRegisterRequest.email());
        assertThat(member.getNickname()).isEqualTo(memberRegisterRequest.nickname());
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void duplicateEmail() throws JsonProcessingException {
        MemberRegisterRequest memberRegisterRequest = MemberFixture.createMemberRegisterRequest();
        memberRegister.register(memberRegisterRequest);

        String registerJson = objectMapper.writeValueAsString(memberRegisterRequest);

        MvcTestResult result = mvcTester.post().uri("/api/members").contentType(MediaType.APPLICATION_JSON)
                .content(registerJson).exchange();

        assertThat(result)
                .apply(print())
                .hasStatus(HttpStatus.CONFLICT);
    }

}
