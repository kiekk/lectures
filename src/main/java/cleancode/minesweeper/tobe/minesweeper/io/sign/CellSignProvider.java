package cleancode.minesweeper.tobe.minesweeper.io.sign;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;
import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshotStatus;

import java.util.Arrays;

public enum CellSignProvider implements CellSignProvidable {
    EMPTY(CellSnapshotStatus.EMPTY) {
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return EMPTY_SIGN;
        }

        @Override
        public boolean supports(CellSnapshot cellSnapshot) {
            return cellSnapshot.isSameStatus(CellSnapshotStatus.EMPTY);
        }
    },
    FLAG(CellSnapshotStatus.FLAG) {
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return FLAG_SIGN;
        }

        @Override
        public boolean supports(CellSnapshot cellSnapshot) {
            return cellSnapshot.isSameStatus(CellSnapshotStatus.FLAG);
        }
    },
    LAND_MINE(CellSnapshotStatus.LAND_MINE) {
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return LAND_MINE_SIGN;
        }

        @Override
        public boolean supports(CellSnapshot cellSnapshot) {
            return cellSnapshot.isSameStatus(CellSnapshotStatus.LAND_MINE);
        }
    },
    NUMBER(CellSnapshotStatus.NUMBER) {
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return String.valueOf(cellSnapshot.getNearbyLandMineCount());
        }

        @Override
        public boolean supports(CellSnapshot cellSnapshot) {
            return cellSnapshot.isSameStatus(CellSnapshotStatus.NUMBER);
        }
    },
    UNCHECKED(CellSnapshotStatus.UNCHECKED) {
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return UNCHECKED_SIGN;
        }

        @Override
        public boolean supports(CellSnapshot cellSnapshot) {
            return cellSnapshot.isSameStatus(CellSnapshotStatus.UNCHECKED);
        }
    },
    ;

    private static final String EMPTY_SIGN = "■";
    private static final String FLAG_SIGN = "⚑";
    private static final String LAND_MINE_SIGN = "☼";
    private static final String UNCHECKED_SIGN = "□";
    private final CellSnapshotStatus status;

    CellSignProvider(CellSnapshotStatus status) {
        this.status = status;
    }

    @Override
    public String provide(CellSnapshot cellSnapshot) {
        return "";
    }

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.isSameStatus(status);
    }

    public static String findCellSignFrom(CellSnapshot snapshot) {
        CellSignProvider cellSignProvider = findBy(snapshot);
        return cellSignProvider.provide(snapshot);
    }

    private static CellSignProvider findBy(CellSnapshot snapshot) {
        return Arrays.stream(values())
                .filter(provider -> provider.supports(snapshot))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("확인할 수 없는 셀입니다."));
    }
}
