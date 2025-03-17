package cleancode.minesweeper.tobe.position;

import cleancode.minesweeper.tobe.cell.Cell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cleancode.minesweeper.tobe.GameApplication.ZERO;

public class CellPositions {

    private final List<CellPosition> cellPositions;

    private CellPositions(List<CellPosition> cellPositions) {
        this.cellPositions = cellPositions;
    }

    public static CellPositions of(List<CellPosition> cellPositions) {
        return new CellPositions(cellPositions);
    }

    public static CellPositions from(Cell[][] board) {
        List<CellPosition> cellPositions = new ArrayList<>();

        for (int row = ZERO; row < board.length; row++) {
            for (int col = ZERO; col < board[0].length; col++) {
                CellPosition cellPosition = CellPosition.of(row, col);
                cellPositions.add(cellPosition);
            }
        }

        return of(cellPositions);
    }

    public List<CellPosition> getPositions() {
        return new ArrayList<>(cellPositions);
    }

    public List<CellPosition> extractRandomPositions(int count) {
        ArrayList<CellPosition> positions = new ArrayList<>(cellPositions);
        Collections.shuffle(positions);
        return positions.subList(ZERO, count);
    }

    public List<CellPosition> subtract(List<CellPosition> positionListToSubtract) {
        ArrayList<CellPosition> cellPositions = new ArrayList<>(this.cellPositions);
        CellPositions positionsToSubtract = CellPositions.of(positionListToSubtract);

        return cellPositions.stream()
                .filter(positionsToSubtract::doesNotContain)
                .toList();
    }

    private boolean doesNotContain(CellPosition position) {
        return !cellPositions.contains(position);
    }
}
