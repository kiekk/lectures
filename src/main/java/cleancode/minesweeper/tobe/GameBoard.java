package cleancode.minesweeper.tobe;

import java.util.Arrays;
import java.util.Random;

import static cleancode.minesweeper.tobe.Minesweeper.ONE;
import static cleancode.minesweeper.tobe.Minesweeper.ZERO;

public class GameBoard {

    public static final int LAND_MINE_COUNT = 10;

    private final Cell[][] board;

    public GameBoard(int rowSize, int colSize) {
        board = new Cell[rowSize][colSize];
    }

    public void initializeGame() {
        int rowSize = getRowSize();
        int colSize = getColSize();

        for (int row = ZERO; row < rowSize; row++) {
            for (int col = ZERO; col < colSize; col++) {
                board[row][col] = Cell.create();
            }
        }

        for (int i = ZERO; i < LAND_MINE_COUNT; i++) {
            int landMineCol = new Random().nextInt(colSize);
            int landMineRow = new Random().nextInt(rowSize);
            Cell landMineCell = findCell(landMineRow, landMineCol);
            landMineCell.turnOnLandMine();
        }

        for (int row = ZERO; row < rowSize; row++) {
            for (int col = ZERO; col < colSize; col++) {
                if (isLandMineCell(row, col)) {
                    continue;
                }

                int nearbyLandMineCount = countNearbyLandMines(row, col);
                Cell cell = findCell(row, col);
                cell.updateNearbyLandMineCount(nearbyLandMineCount);
            }
        }
    }

    public int getRowSize() {
        return board.length;
    }

    public int getColSize() {
        return board[0].length;
    }

    public String getSign(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        return cell.getSign();
    }

    public void flag(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        cell.flag();
    }

    public void open(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        cell.open();
    }

    public boolean isLandMineCell(int selectedRowIndex, int selectedColIndex) {
        Cell cell = findCell(selectedRowIndex, selectedColIndex);
        return cell.isLandMine();
    }

    private int countNearbyLandMines(int rowIndex, int colIndex) {
        int rowSize = getRowSize();
        int colSize = getColSize();
        int count = ZERO;

        if (rowIndex - ONE >= ZERO && colIndex - ONE >= ZERO && isLandMineCell(rowIndex - ONE, colIndex - ONE)) {
            count++;
        }
        if (rowIndex - ONE >= ZERO && isLandMineCell(rowIndex - ONE, colIndex)) {
            count++;
        }
        if (rowIndex - ONE >= ZERO && colIndex + ONE < colSize && isLandMineCell(rowIndex - ONE, colIndex + ONE)) {
            count++;
        }
        if (colIndex - ONE >= ZERO && isLandMineCell(rowIndex, colIndex - ONE)) {
            count++;
        }
        if (colIndex + ONE < colSize && isLandMineCell(rowIndex, colIndex + ONE)) {
            count++;
        }
        if (rowIndex + ONE < rowSize && colIndex - ONE >= ZERO && isLandMineCell(rowIndex + ONE, colIndex - ONE)) {
            count++;
        }
        if (rowIndex + ONE < rowSize && isLandMineCell(rowIndex + ONE, colIndex)) {
            count++;
        }
        if (rowIndex + ONE < rowSize && colIndex + ONE < colSize && isLandMineCell(rowIndex + ONE, colIndex + ONE)) {
            count++;
        }
        return count;
    }

    private Cell findCell(int rowIndex, int colIndex) {
        return board[rowIndex][colIndex];
    }

    public void openSurroundedCells(int rowIndex, int colIndex) {
        if (rowIndex < ZERO || rowIndex >= getRowSize() || colIndex < ZERO || colIndex >= getColSize()) {
            return;
        }

        if (isOpenedCell(rowIndex, colIndex)) {
            return;
        }

        if (isLandMineCell(rowIndex, colIndex)) {
            return;
        }

        open(rowIndex, colIndex);

        if (doesCellHaveLandMineCount(rowIndex, colIndex)) {
            return;
        }

        openSurroundedCells(rowIndex - ONE, colIndex - ONE);
        openSurroundedCells(rowIndex - ONE, colIndex);
        openSurroundedCells(rowIndex - ONE, colIndex + ONE);
        openSurroundedCells(rowIndex, colIndex - ONE);
        openSurroundedCells(rowIndex, colIndex + ONE);
        openSurroundedCells(rowIndex + ONE, colIndex - ONE);
        openSurroundedCells(rowIndex + ONE, colIndex);
        openSurroundedCells(rowIndex + ONE, colIndex + ONE);
    }

    public boolean isAllCellChecked() {
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .allMatch(Cell::isChecked);
    }

    private boolean doesCellHaveLandMineCount(int rowIndex, int colIndex) {
        return findCell(rowIndex, colIndex).hasLandMineCount();
    }

    private boolean isOpenedCell(int rowIndex, int colIndex) {
        return findCell(rowIndex, colIndex).isOpened();
    }
}
