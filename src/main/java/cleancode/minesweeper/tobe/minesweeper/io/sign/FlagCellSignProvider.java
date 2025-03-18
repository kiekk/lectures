package cleancode.minesweeper.tobe.minesweeper.io.sign;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;

import static cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshotStatus.FLAG;

public class FlagCellSignProvider implements CellSignProvidable {

    private static final String FLAG_SIGN = "⚑";

    @Override
    public String provide(CellSnapshot cellSnapshot) {
        return FLAG_SIGN;
    }

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.isSameStatus(FLAG);
    }
}
