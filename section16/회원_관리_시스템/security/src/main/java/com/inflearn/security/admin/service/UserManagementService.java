package com.inflearn.security.admin.service;

import com.inflearn.security.domain.dto.AccountDto;
import com.inflearn.security.domain.entity.Account;

import java.util.List;

public interface UserManagementService {

    void modifyUser(AccountDto accountDto);

    List<Account> getUsers();

    AccountDto getUser(Long id);

    void deleteUser(Long idx);

}
