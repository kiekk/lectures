package hello.proxy.trace;

import lombok.Getter;

import java.util.UUID;

/*
    로그 추적기를 위한 클래스

    로그 추적기는 트랜잭션ID(고유한 아이디)와 깊이를 표현하는 방법(level)이 필요합니다.

 */
@Getter
public class TraceId {

    private String id;
    private int level;

    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    private String createId() {
        // 생성된 UUID의 앞자리 8자리만 사용
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public TraceId createNextId() {
        return new TraceId(id, level + 1);
    }

    public TraceId createPreviousId() {
        return new TraceId(id, level - 1);
    }

    public boolean isFirstLevel() {
        return level == 0;
    }

}
