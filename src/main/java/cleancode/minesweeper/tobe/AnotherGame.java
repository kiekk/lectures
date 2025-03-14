package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.game.GameRunnable;

// AnotherGame 은 초기화가 필요하지 않기 때문에 GameInitializable 인터페이스를 구현하지 않아도 된다.
public class AnotherGame implements GameRunnable {

    @Override
    public void run() {
        // start game
    }
}
