package com.tobyspring.studytobyspring.dao;

public class DaoFactory {

    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }

    public AccountDao accountDao() {
        return new AccountDao(connectionMaker());
    }

    public MessageDao messageDao() {
        return new MessageDao(connectionMaker());
    }

    // ConnectionMaker 생성 중복 코드를 메소드로 분리
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }

}
