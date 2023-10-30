package com.example.pr5.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue
    @Column
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @Column
    private int total;

    @OneToMany(mappedBy = "cart",
            cascade = CascadeType.ALL)
    private Set<ProductInCart> products;

    public void removeProduct(ProductInCart product){
        products.remove(product);
        product.setCart(null);
    }
}
