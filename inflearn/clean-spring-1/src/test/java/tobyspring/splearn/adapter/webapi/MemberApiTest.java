package tobyspring.splearn.adapter.webapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import tobyspring.splearn.application.member.provided.MemberRegister;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberFixture;
import tobyspring.splearn.domain.member.MemberRegisterRequest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(MemberApi.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class MemberApiTest {

    @MockitoBean
    private MemberRegister memberRegister;

    private final MockMvcTester mvcTester;
    private final ObjectMapper objectMapper;

    @Test
    void register() throws JsonProcessingException {
        Member member = MemberFixture.createMember(1L);
        when(memberRegister.register(any())).thenReturn(member);
        MemberRegisterRequest memberRegisterRequest = MemberFixture.createMemberRegisterRequest();
        String registerJson = objectMapper.writeValueAsString(memberRegisterRequest);

        assertThat(mvcTester.post().uri("/api/members").contentType(MediaType.APPLICATION_JSON)
                .content(registerJson))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.memberId").asNumber().isEqualTo(1);

        verify(memberRegister).register(memberRegisterRequest);
    }

    @Test
    void registerFail() throws JsonProcessingException {
        Member member = MemberFixture.createMember(1L);
        when(memberRegister.register(any())).thenReturn(member);
        MemberRegisterRequest memberRegisterRequest = MemberFixture.createMemberRegisterRequest("invalidEmail");
        String registerJson = objectMapper.writeValueAsString(memberRegisterRequest);

        assertThat(mvcTester.post().uri("/api/members").contentType(MediaType.APPLICATION_JSON)
                .content(registerJson))
                .hasStatus4xxClientError();
    }

}