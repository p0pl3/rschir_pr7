package com.example.pr5.controllers;


import com.example.pr5.models.Cart;
import com.example.pr5.models.ProductInCart;
import com.example.pr5.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public Cart getById(@PathVariable("id") int id) {
        return cartService.getById(id);
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{user_id}")
    public Cart addProductInCart(@PathVariable("user_id") int uid, @RequestBody ProductInCart product) {
        int res = cartService.addProductToCart(uid, product.getProduct_id(), String.valueOf(product.getProductType()), product.getQuantity());
        if (res < 0) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "not enoughh products");
        }
        return cartService.getById(uid);
    }
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{product_id}")
    public void deleteProductInCart(@PathVariable("product_id") int product_id) {
        cartService.delete_product_in_cart(product_id);
    }
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{product_in_cart_id}")
    public void updateQuantity(@PathVariable("product_in_cart_id") int pid, @RequestParam("quantity") int q) {
        cartService.change_quantity(pid, q);
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{user_id}/buy")
    public String buy(@PathVariable("user_id") int pid) {
        return cartService.buy(pid);
    }

}
