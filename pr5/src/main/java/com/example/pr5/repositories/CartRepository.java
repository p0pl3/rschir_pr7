package com.example.pr5.repositories;


import com.example.pr5.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart getCartByClient_Id (Integer client_id);
}
