package cleancode.minesweeper.tobe.minesweeper.io.sign;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;

import static cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshotStatus.EMPTY;

public class EmptyCellSignProvider implements CellSignProvidable {

    private static final String EMPTY_SIGN = "■";

    @Override
    public String provide(CellSnapshot cellSnapshot) {
        return EMPTY_SIGN;
    }

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.isSameStatus(EMPTY);
    }
}
