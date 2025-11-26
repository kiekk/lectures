package tobyspring.splearn.domain.member;

public class MemberFixture {
    public static MemberRegisterRequest createMemberRegisterRequest() {
        return createMemberRegisterRequest("soono@splearn.app");
    }

    public static MemberRegisterRequest createMemberRegisterRequest(String mail) {
        return new MemberRegisterRequest(mail, "soono", "secret1234");
    }

    public static PasswordEncoder createPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
    }
}
