package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.Cell;
import cleancode.minesweeper.tobe.cell.EmptyCell;
import cleancode.minesweeper.tobe.cell.LandMineCell;
import cleancode.minesweeper.tobe.cell.NumberCell;
import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.position.CellPosition;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static cleancode.minesweeper.tobe.GameApplication.ZERO;
import static cleancode.minesweeper.tobe.position.RelativePosition.SURROUND_RELATIVE_POSITIONS;

public class GameBoard {

    private final int landMineCount;
    private final Cell[][] board;

    public GameBoard(GameLevel gameLevel) {
        int rowSize = gameLevel.getRowSize();
        int colSize = gameLevel.getColSize();
        this.board = new Cell[rowSize][colSize];

        this.landMineCount = gameLevel.getLandMineCount();
    }

    public void initializeGame() {
        int rowSize = getRowSize();
        int colSize = getColSize();

        for (int row = ZERO; row < rowSize; row++) {
            for (int col = ZERO; col < colSize; col++) {
                board[row][col] = new EmptyCell();
            }
        }

        for (int i = ZERO; i < landMineCount; i++) {
            int landMineCol = new Random().nextInt(colSize);
            int landMineRow = new Random().nextInt(rowSize);
            board[landMineRow][landMineCol] = new LandMineCell();
        }

        for (int row = ZERO; row < rowSize; row++) {
            for (int col = ZERO; col < colSize; col++) {
                CellPosition cellPosition = CellPosition.of(row, col);
                if (isLandMineCell(cellPosition)) {
                    continue;
                }

                int nearbyLandMineCount = countNearbyLandMines(cellPosition);

                if (nearbyLandMineCount == ZERO) {
                    continue;
                }
                board[row][col] = new NumberCell(nearbyLandMineCount);
            }
        }
    }

    public int getRowSize() {
        return board.length;
    }

    public int getColSize() {
        return board[0].length;
    }

    public String getSign(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.getSign();
    }

    public void flag(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.flag();
    }

    public void openAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.open();
    }

    public boolean isLandMineCell(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.isLandMine();
    }

    private int countNearbyLandMines(CellPosition cellPosition) {
        int rowSize = getRowSize();
        int colSize = getColSize();

        return (int) calculateSurroundedPositions(cellPosition, rowSize, colSize).stream()
                .filter(this::isLandMineCell)
                .count();
    }

    private List<CellPosition> calculateSurroundedPositions(CellPosition cellPosition, int rowSize, int colSize) {
        return SURROUND_RELATIVE_POSITIONS.stream()
                .filter(cellPosition::canCalculatePositionBy)
                .map(cellPosition::calculatePositionBy)
                .filter(position -> position.isRowIndexLessThan(rowSize))
                .filter(position -> position.isColIndexLessThan(colSize))
                .toList();
    }

    private Cell findCell(CellPosition cellPosition) {
        return board[cellPosition.getRowIndex()][cellPosition.getColIndex()];
    }

    public void openSurroundedCells(CellPosition cellPosition) {
        if (isOpenedCell(cellPosition)) {
            return;
        }

        if (isLandMineCell(cellPosition)) {
            return;
        }

        openAt(cellPosition);

        if (doesCellHaveLandMineCount(cellPosition)) {
            return;
        }

        calculateSurroundedPositions(cellPosition, getRowSize(), getColSize())
                .forEach(this::openSurroundedCells);
    }

    public boolean isAllCellChecked() {
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .allMatch(Cell::isChecked);
    }

    private boolean doesCellHaveLandMineCount(CellPosition cellPosition) {
        return findCell(cellPosition).hasLandMineCount();
    }

    private boolean isOpenedCell(CellPosition cellPosition) {
        return findCell(cellPosition).isOpened();
    }

}
