package cleancode.minesweeper.tobe.game;

// initialize가 필요없는 구현체일 경우에도 반드시 해당 메서드를 구현해야 하는 단점이 있다.
public interface Game {
    void initialize();

    void run();
}
