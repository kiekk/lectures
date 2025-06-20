package io.security.corespringsecurity.service;

import io.security.corespringsecurity.domain.entity.Account;
import io.security.corespringsecurity.domain.dto.AccountDto;

import java.util.List;

public interface UserService {

    void createUser(Account account);

    void modifyUser(AccountDto accountDto);

    List<Account> getUsers();

    AccountDto getUser(Long id);

    void deleteUser(Long idx);
}