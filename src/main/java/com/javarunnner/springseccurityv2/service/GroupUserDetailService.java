package com.javarunnner.springseccurityv2.service;

import com.javarunnner.springseccurityv2.entity.User;
import com.javarunnner.springseccurityv2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<User> user =userRepository.findByUserName(username);
       //user.orElseThrow(() -> new UsernameNotFoundException("Not found! "+username));
        return user.map(s -> new GroupUserDetail(s)).orElseThrow(() -> new UsernameNotFoundException("user name does not exist in system "+username));

    }
}
