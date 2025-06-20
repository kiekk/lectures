package cleancode.minesweeper.tobe.minesweeper.io.sign;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;

import static cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshotStatus.UNCHECKED;

public class UncheckedCellSignProvider implements CellSignProvidable {

    private static final String UNCHECKED_SIGN = "□";

    @Override
    public String provide(CellSnapshot cellSnapshot) {
        return UNCHECKED_SIGN;
    }

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.isSameStatus(UNCHECKED);
    }
}
