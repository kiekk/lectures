package com.inflearn.security.config.admin.repository;

import com.inflearn.security.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserManagementRepository extends JpaRepository<Account, Long> {

}
