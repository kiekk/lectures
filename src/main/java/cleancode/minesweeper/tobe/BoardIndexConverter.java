package cleancode.minesweeper.tobe;

import static cleancode.minesweeper.tobe.GameApplication.ONE;
import static cleancode.minesweeper.tobe.GameApplication.ZERO;

public class BoardIndexConverter {

    private static final char BASE_CHAR_FOR_COL = 'a';

    public int getSelectedRowIndex(String cellInput) {
        String cellInputRow = cellInput.substring(ONE);
        return convertRowFrom(cellInputRow);
    }

    public int getSelectedColIndex(String cellInput) {
        char cellInputCol = cellInput.charAt(ZERO);
        return convertColFrom(cellInputCol);
    }


    private int convertRowFrom(String cellInputRow) {
        int rowIndex = Integer.parseInt(cellInputRow) - ONE;

        if (rowIndex < 0 ) {
            throw new GameException("잘못된 입력입니다.");
        }

        return rowIndex;
    }

    private int convertColFrom(char cellInputCol) {
        int colIndex = cellInputCol - BASE_CHAR_FOR_COL;

        if (colIndex < 0) {
            throw new GameException("잘못된 입력입니다.");
        }

        return colIndex;
    }
}
