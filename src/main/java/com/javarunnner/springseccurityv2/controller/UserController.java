package com.javarunnner.springseccurityv2.controller;

import com.javarunnner.springseccurityv2.common.UserConstants;
import com.javarunnner.springseccurityv2.entity.User;
import com.javarunnner.springseccurityv2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @PostMapping("/join")
    public String joinGroup(@RequestBody User user){
        user.setRoles(UserConstants.DEFAULT_ROLE);
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        return "Hello "+user.getUserName()+" welcome to group!";
    }

    //If logged in user is ADMIN -> ADMIN OR MODERATOR
    //If loggedn in user is MODERATOR -> MODERATOR
    @GetMapping("/access/{userId}/{userRole}")
    //@Secured("ROLE_ADMIN")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public String giveAccessToUser(@PathVariable Integer userId, @PathVariable String userRole , Principal principal){
      User user= userRepository.findById(userId).get();
      List<String> activeRoles =  getRolesByLoggedInUsers(principal);
      String newRole="";
      if (activeRoles.contains(userRole)){
            newRole=user.getRoles()+","+userRole;
            user.setRoles(newRole);
      }
      userRepository.save(user);
      return "Hi "+user.getUserName()+" New Role assign to you by "+principal.getName();
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> loadUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/test")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String testUserAccess(){
       return "user can only access this.";
    }


    private List<String> getRolesByLoggedInUsers(Principal principal){
        String roles = getLoggedInUser(principal).getRoles();
        List<String> assignedRoles = Arrays.stream(roles.split(",")).collect(Collectors.toList());
        if (assignedRoles.contains("ROLE_ADMIN")){
            return Arrays.stream(UserConstants.ADMIN_ACCESS).collect(Collectors.toList());
        }else if (assignedRoles.contains("ROLE_MODERATOR")){
            return Arrays.stream(UserConstants.MODERATOR_ACCESS).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
    private User getLoggedInUser(Principal principal){
        return userRepository.findByUserName(principal.getName()).get();
    }

}
