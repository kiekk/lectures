package tobyspring.splearn.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.annotations.Comment;

import java.util.regex.Pattern;

@Embeddable
public record Email(
        @Comment("이메일 주소") @Column(name = "email_address", length = 150, nullable = false) String value) {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public Email {
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다. email: " + value);
        }
    }
}
