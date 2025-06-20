package com.mvc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberRepository {
    private static final Map<String, Member> store = new HashMap<>();

    // 회원 저장
    public void save(Member member) {
        store.put(member.getUsername(), member);
    }

    // 전체 회원 조회
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
}
