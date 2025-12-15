package tobyspring.splearn;

import org.assertj.core.api.AssertProvider;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.springframework.test.json.JsonPathValueAssert;
import tobyspring.splearn.domain.member.MemberRegisterRequest;

import java.util.function.Consumer;

public class AssertThatUtils {
    public static Consumer<AssertProvider<JsonPathValueAssert>> notNull() {
        return value -> AssertionsForInterfaceTypes.assertThat(value).isNotNull();
    }

    public static Consumer<AssertProvider<JsonPathValueAssert>> equalsTo(MemberRegisterRequest memberRegisterRequest) {
        return value -> AssertionsForInterfaceTypes.assertThat(value).isEqualTo(memberRegisterRequest.email());
    }
}
