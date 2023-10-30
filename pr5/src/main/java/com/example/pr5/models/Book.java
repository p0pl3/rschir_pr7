package com.example.pr5.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue
    @Column
    private int id;
    @Column
    private String name;
    @Column
    private int price;
    @Column
    private int seller_id;
    @Column
    @Enumerated(EnumType.STRING)
    private ProductType productType = ProductType.BOOK;
    @Column
    private String author;
    @Column
    private int total_q;
}
