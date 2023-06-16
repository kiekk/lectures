package io.security.oauth2.inflearnspringsecurityoauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashSet;

public class CustomAuthorityMapper implements GrantedAuthoritiesMapper {

    private GrantedAuthority defaultAuthority;

    private String prefix = "ROLE_";

    private boolean convertToUpperCase = false;

    private boolean convertToLowerCase = false;

    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
        HashSet<GrantedAuthority> mapped = new HashSet<>(authorities.size());
        for (GrantedAuthority authority : authorities) {
            mapped.add(mapAuthority(authority.getAuthority()));
        }
        return mapped;
    }

    private GrantedAuthority mapAuthority(String name) {
        if (name.lastIndexOf(".") > 0) {
            int index = name.lastIndexOf(".");
            name = "SCOPE_" + name.substring(index + 1);
        }

        if (StringUtils.hasText(prefix) && !name.startsWith(prefix)) {
            name = prefix + name;
        }

        return new SimpleGrantedAuthority(name);
    }
}
