package com.security.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Users {


//    String userName;
//    String password;
//    String email;

    @Id
    private int id;
    private String userName;
    private String password;

}
