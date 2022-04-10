package com.javarunnner.springseccurityv2.repository;

import com.javarunnner.springseccurityv2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByUserName(String username);

}
