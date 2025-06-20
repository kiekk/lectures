package shop.mtcoding.bank.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserEnum {
    ADMIN("관리자"), CUSTOMER("고객");

    private final String value;

}
