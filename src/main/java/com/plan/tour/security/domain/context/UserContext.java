package com.plan.tour.security.domain.context;

import com.plan.tour.security.domain.ContextDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserContext extends User {

    private final ContextDto contextDto;

    public ContextDto getContextDto() {
        return contextDto;
    }

    public UserContext(ContextDto contextDto, Collection<? extends GrantedAuthority> authorities) {
        super(contextDto.getUsername(), contextDto.getPassword(), authorities);
        this.contextDto = contextDto;
    }
}
