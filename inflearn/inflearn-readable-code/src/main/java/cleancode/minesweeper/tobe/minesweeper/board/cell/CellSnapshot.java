package cleancode.minesweeper.tobe.minesweeper.board.cell;

import java.util.Objects;

import static cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshotStatus.*;

public class CellSnapshot {
    private final CellSnapshotStatus status;
    private final int nearbyLandMineCount;

    private CellSnapshot(CellSnapshotStatus status, int nearbyLandMineCount) {
        this.status = status;
        this.nearbyLandMineCount = nearbyLandMineCount;
    }

    public static CellSnapshot of(CellSnapshotStatus status, int nearbyLandMineCount) {
        return new CellSnapshot(status, nearbyLandMineCount);
    }

    public static CellSnapshot ofEmpty() {
        return new CellSnapshot(EMPTY, 0);
    }

    public static CellSnapshot ofFlag() {
        return new CellSnapshot(FLAG, 0);
    }

    public static CellSnapshot ofLandMine() {
        return new CellSnapshot(LAND_MINE, 0);
    }

    public static CellSnapshot ofNumber(int nearbyLandMineCount) {
        return new CellSnapshot(NUMBER, nearbyLandMineCount);
    }

    public static CellSnapshot ofUnchecked() {
        return new CellSnapshot(UNCHECKED, 0);
    }

    public boolean isSameStatus(CellSnapshotStatus status) {
        return this.status == status;
    }

    public int getNearbyLandMineCount() {
        return nearbyLandMineCount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CellSnapshot that = (CellSnapshot) o;
        return nearbyLandMineCount == that.nearbyLandMineCount && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, nearbyLandMineCount);
    }

}
