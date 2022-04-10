package com.javarunnner.springseccurityv2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="USER_AUTH_TBL")
public class User {

    @Id
    @GeneratedValue
    private Integer id;
    private String userName;
    private String password;
    private boolean active;
    private String roles;



}
