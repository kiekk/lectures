package com.soono.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemberRepository {

    private static final Map<String, Member> store = new ConcurrentHashMap<>();

    // 회원 저장
    public void save(Member member) {
        store.put(member.getUsername(), member);
    }

    // 전체 회원 조회
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
}
