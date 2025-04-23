package com.inflearn.security.config.security.listener;

import com.inflearn.security.admin.repository.RoleRepository;
import com.inflearn.security.domain.entity.Account;
import com.inflearn.security.domain.entity.Role;
import com.inflearn.security.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private boolean alreadySetup = false;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final FilterChainProxy filterChainProxy;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        // 기본 AuthorizationFilter를 제거하는 것이기 때문에 CustomAuthorizationFilter를 사용하지 않을 거라면 해당 코드 주석
//        disableAuthorizationFilter();
        setupData();
        alreadySetup = true;
    }

    private void disableAuthorizationFilter() {
        filterChainProxy.getFilterChains()
                .forEach(df -> df.getFilters().removeLast());
    }

    private void setupData() {
        Set<Role> roles = new HashSet<>();
        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", "관리자");
        roles.add(adminRole);
        createUserIfNotFound("admin", "admin@admin.com", "pass", roles);
    }

    public Role createRoleIfNotFound(String roleName, String roleDesc) {
        Role role = roleRepository.findByRoleName(roleName);

        if (role == null) {
            role = Role.builder()
                    .roleName(roleName)
                    .roleDesc(roleDesc)
                    .build();
        }
        return roleRepository.save(role);
    }

    public void createUserIfNotFound(final String userName, final String email, final String password, Set<Role> roleSet) {
        Account account = userRepository.findByUsername(userName);

        if (account == null) {
            account = Account.builder()
                    .username(userName)
                    .password(passwordEncoder.encode(password))
                    .userRoles(roleSet)
                    .build();
        }
        userRepository.save(account);
    }
}
