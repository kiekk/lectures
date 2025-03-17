package cleancode.minesweeper.tobe.position;

import java.util.List;
import java.util.Objects;

import static cleancode.minesweeper.tobe.GameApplication.ONE;
import static cleancode.minesweeper.tobe.GameApplication.ZERO;

public class RelativePosition {

    public static final List<RelativePosition> SURROUND_RELATIVE_POSITIONS = List.of(
            RelativePosition.of(-ONE, -ONE),
            RelativePosition.of(-ONE, ZERO),
            RelativePosition.of(-ONE, ONE),
            RelativePosition.of(ZERO, -ONE),
            RelativePosition.of(ZERO, ONE),
            RelativePosition.of(ONE, -ONE),
            RelativePosition.of(ONE, ZERO),
            RelativePosition.of(ONE, ONE)
    );

    private final int deltaRow;
    private final int deltaCol;

    private RelativePosition(int deltaRow, int deltaCol) {
        this.deltaRow = deltaRow;
        this.deltaCol = deltaCol;
    }

    public static RelativePosition of(int deltaRow, int deltaCol) {
        return new RelativePosition(deltaRow, deltaCol);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RelativePosition that = (RelativePosition) o;
        return deltaRow == that.deltaRow && deltaCol == that.deltaCol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(deltaRow, deltaCol);
    }

    public int getDeltaRow() {
        return deltaRow;
    }

    public int getDeltaCol() {
        return deltaCol;
    }
}
