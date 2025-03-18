package cleancode.studycafe.tobe.model.pass;

import java.util.Set;

public enum StudyCafePassType {

    HOURLY("시간 단위 이용권"),
    WEEKLY("주 단위 이용권"),
    FIXED("1인 고정석");

    private final String description;

    private static final Set<StudyCafePassType> LOCKER_TYPES = Set.of(StudyCafePassType.FIXED);

    StudyCafePassType(String description) {
        this.description = description;
    }

    public boolean isNotLockerType() {
        return !LOCKER_TYPES.contains(this);
    }
}
