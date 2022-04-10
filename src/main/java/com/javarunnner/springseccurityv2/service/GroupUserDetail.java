package com.javarunnner.springseccurityv2.service;

import com.javarunnner.springseccurityv2.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GroupUserDetail implements UserDetails {
    private String userName;
    private String password;
    private boolean isActive;
    private List<GrantedAuthority> grantedAuthorities;

    public GroupUserDetail(User user){
        this.userName= user.getUserName();
        this.password=user.getPassword();
        this.isActive= user.isActive();
        this.grantedAuthorities = Arrays.stream(user.getRoles().split(",")).map(grntAuth -> new SimpleGrantedAuthority(grntAuth)).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }
}
