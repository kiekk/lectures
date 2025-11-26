package tobyspring.splearn.domain.member;

import java.util.regex.Pattern;

public record Profile(String address) {
    private static final Pattern PROFILE_ADDRESS_PATTERN = Pattern.compile("^[a-z0-9]+$");
    public static final int MAX_PROFILE_ADDRESS_LENGTH = 15;

    public Profile {
        if (!PROFILE_ADDRESS_PATTERN.matcher(address).matches()) {
            throw new IllegalArgumentException("프로필 주소 형식이 올바르지 않습니다. profile: " + address);
        }

        if(address.length() > MAX_PROFILE_ADDRESS_LENGTH) {
            throw new IllegalArgumentException("프로필 주소는 155자를 초과할 수 없습니다. profile: " + address);
        }
    }

    public String url() {
        return "@" + address;
    }
}
