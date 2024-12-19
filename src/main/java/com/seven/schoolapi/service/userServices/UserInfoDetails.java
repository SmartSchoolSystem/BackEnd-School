package com.seven.schoolapi.service.userServices;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.seven.schoolapi.entity.UserInfo;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserInfoDetails implements UserDetails {

    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private Collection<GrantedAuthority> authorities;

    public UserInfoDetails(UserInfo userInfo) {
        this.id = userInfo.getId();
        this.name = userInfo.getName();
        this.username = userInfo.getUsername();
        this.password = userInfo.getPassword();
        this.email = userInfo.getEmail();
        this.authorities = Arrays.stream(userInfo.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
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
        return true;
    }

    public Long getId() {
        return id;
    }
}