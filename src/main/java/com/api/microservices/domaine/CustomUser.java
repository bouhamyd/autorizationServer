package com.api.microservices.domaine;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;


public class CustomUser extends User {
    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("username", getUsername())
                .append("authorities", getAuthorities())
                .toString();
    }
}
