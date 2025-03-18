package cleancode.studycafe.tobe.model.pass.seat;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;

import java.util.List;

public class StudyCafeSeatPasses {
    private final List<StudyCafeSeatPass> seatPasses;

    private StudyCafeSeatPasses(List<StudyCafeSeatPass> seatPasses) {
        this.seatPasses = seatPasses;
    }

    public static StudyCafeSeatPasses of(List<StudyCafeSeatPass> seatPasses) {
        return new StudyCafeSeatPasses(seatPasses);
    }

    public List<StudyCafeSeatPass> findPassBy(StudyCafePassType studyCafePassType) {
        return seatPasses.stream()
                .filter(pass -> pass.isSamePassType(studyCafePassType))
                .toList();
    }

}
