package tobyspring.splearn.application.provided;

import tobyspring.splearn.domain.MemberRegisterRequest;
import tobyspring.splearn.domain.Member;

/**
 * 회원의 등록과 관련된 기능을 제공한다.
 */
public interface MemberRegister {
    Member register(MemberRegisterRequest registerRequest);
}
