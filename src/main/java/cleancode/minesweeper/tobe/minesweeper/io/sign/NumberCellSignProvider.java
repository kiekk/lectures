package cleancode.minesweeper.tobe.minesweeper.io.sign;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;

import static cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshotStatus.NUMBER;

public class NumberCellSignProvider implements CellSignProvidable {

    @Override
    public String provide(CellSnapshot cellSnapshot) {
        return String.valueOf(cellSnapshot.getNearbyLandMineCount());
    }

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.isSameStatus(NUMBER);
    }
}
