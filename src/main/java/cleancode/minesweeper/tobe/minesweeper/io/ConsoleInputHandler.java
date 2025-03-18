package cleancode.minesweeper.tobe.minesweeper.io;

import cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import cleancode.minesweeper.tobe.minesweeper.user.UserAction;

import java.util.Scanner;

public class ConsoleInputHandler implements InputHandler {

    public static final Scanner SCANNER = new Scanner(System.in);
    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();

    @Override
    public UserAction getUserActionFromUser() {
        String userInput = SCANNER.nextLine();

        return switch (userInput) {
            case "1" -> UserAction.OPEN;
            case "2" -> UserAction.FLAG;
            default -> UserAction.UNKNOWN;
        };
    }

    @Override
    public CellPosition getCellPositionFromUser() {
        String userInput = SCANNER.nextLine();

        int colIndex = boardIndexConverter.getSelectedColIndex(userInput);
        int rowIndex = boardIndexConverter.getSelectedRowIndex(userInput);

        return CellPosition.of(rowIndex, colIndex);
    }
}
