package com.example.pr5.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue
    @Column
    private int id;
    @Column
    private String email;
    @Column
    private String login;
    @Column
    private String password;
}
