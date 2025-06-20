package cleancode.studycafe.tobe.model.pass.seat;

import cleancode.studycafe.tobe.model.pass.StudyCafePass;
import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;

public class StudyCafeSeatPass implements StudyCafePass {

    private final StudyCafePassType passType;
    private final int duration;
    private final int price;
    private final double discountRate;

    private StudyCafeSeatPass(StudyCafePassType passType, int duration, int price, double discountRate) {
        this.passType = passType;
        this.duration = duration;
        this.price = price;
        this.discountRate = discountRate;
    }

    public static StudyCafeSeatPass of(StudyCafePassType passType, int duration, int price, double discountRate) {
        return new StudyCafeSeatPass(passType, duration, price, discountRate);
    }

    public boolean isSamePassType(StudyCafePassType passType) {
        return this.passType == passType;
    }

    public boolean isSameDurationType(StudyCafeLockerPass lockerPass) {
        return lockerPass.isSameDuration(duration) && lockerPass.isSamePassType(passType);
    }

    public boolean cannotUseLocker() {
        return !this.passType.isNotLockerType();
    }

    public int getDiscountPrice() {
        return (int) (price * discountRate);
    }

    @Override
    public StudyCafePassType getPassType() {
        return passType;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public int getDuration() {
        return duration;
    }

}
