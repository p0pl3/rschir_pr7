package com.example.pr5.service;


import com.example.pr5.models.*;
import com.example.pr5.repositories.CartRepository;
import com.example.pr5.repositories.ProductInCartRepository;
import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    BookService bookService;

    @Autowired
    TelephoneService telephoneService;

    @Autowired
    WashingMachineService washingMachineService;

    @Autowired
    ProductInCartRepository productInCartRepository;

    @Autowired
    CartRepository cartRepository;

    public Cart createCart(Cart cart) {
        cartRepository.save(cart);
        return cart;
    }

    public List<Cart> get_all() {
        return new ArrayList<>(cartRepository.findAll());
    }

    public Cart getById(int id) {
        return cartRepository.getCartByClient_Id(id);
    }

    public void create(Cart clients) {
        cartRepository.save(clients);
    }

    public void delete(int id) {
        cartRepository.deleteById(id);
    }

    //updating a record
    public void update(Cart clients, int Clientid) {
        cartRepository.save(clients);
    }


    public void recalculateTotal(int cart_id) {
        List<ProductInCart> products = productInCartRepository.findProductInCartByCart_Id(cart_id);
        Cart cart = this.getById(cart_id);
        int sum = 0;
        for (var i : products) {
            if (i.getProductType() == ProductType.BOOK) {
                Book book = bookService.getById(i.getProduct_id());
                sum += book.getPrice() * i.getQuantity();
            } else if (i.getProductType() == ProductType.PHONE) {
                Telephone telephone = telephoneService.getById(i.getProduct_id());
                sum += telephone.getPrice() * i.getQuantity();
            } else if (i.getProductType() == ProductType.WAHSING_MASHINE) {
                WashingMachine washingMachine = washingMachineService.getById(i.getProduct_id());
                sum += washingMachine.getPrice() * i.getQuantity();
            }
        }
        cart.setTotal(sum);
        cartRepository.save(cart);
    }

    public int addProductToCart(int user_id, int product_id, String product_type, int quantity) {
        Cart cart = cartRepository.getCartByClient_Id(user_id);
        ProductInCart product = new ProductInCart();
        product.setCart(cart);
        product.setProduct_id(product_id);
        product.setQuantity(quantity);

        if (product_type.equalsIgnoreCase(String.valueOf(ProductType.BOOK))) {
            Book book = bookService.getById(product_id);
            if (quantity > book.getTotal_q()) return -1;
            product.setProductType(ProductType.BOOK);
            cart.setTotal(quantity*book.getPrice());
        } else if (product_type.equalsIgnoreCase(String.valueOf(ProductType.PHONE))) {
            Telephone book = telephoneService.getById(product_id);
            if (quantity > book.getTotal_q()) return -1;
            product.setProductType(ProductType.PHONE);
            cart.setTotal(quantity*book.getPrice());
        } else if (product_type.equalsIgnoreCase(String.valueOf(ProductType.WAHSING_MASHINE))) {
            WashingMachine book = washingMachineService.getById(product_id);
            if (quantity > book.getTotal_q()) return -1;
            product.setProductType(ProductType.WAHSING_MASHINE);
            cart.setTotal(quantity*book.getPrice());
        }


        productInCartRepository.save(product);
        return 1;
    }

    public void change_quantity(int product_in_cart_id, int q) {
        ProductInCart product = productInCartRepository.getReferenceById(product_in_cart_id);
        int available_q = 0;

        if (product.getProductType() == ProductType.BOOK) {
            Book book = bookService.getById(product.getProduct_id());
            available_q = book.getTotal_q();
        } else if (product.getProductType() == ProductType.PHONE) {
            Telephone telephone = telephoneService.getById(product.getProduct_id());
            available_q = telephone.getTotal_q();
        } else if (product.getProductType() == ProductType.WAHSING_MASHINE) {
            WashingMachine washingMachine = washingMachineService.getById(product.getProduct_id());
            available_q = washingMachine.getTotal_q();
        }

        if (available_q < q) {
            return;
        }
        product.setQuantity(q);
        productInCartRepository.save(product);
        this.recalculateTotal(product.getCart().getId());
    }

    public void delete_product_in_cart(int product_ic_id) {
        System.out.println(product_ic_id);
        ProductInCart pc = productInCartRepository.findById(product_ic_id).get();
        Cart c = pc.getCart();
        c.setProducts(c.getProducts().stream().filter(product -> product.getId() != product_ic_id).collect(Collectors.toSet()));
        cartRepository.save(c);
        productInCartRepository.deleteById(product_ic_id);
    }

    public String buy(int user_id) {
        Cart cart = cartRepository.getCartByClient_Id(user_id);
        // check availability
        for (var i : cart.getProducts()) {
            if (i.getProductType() == ProductType.BOOK) {
                Book book = bookService.getById(i.getProduct_id());
                if (i.getQuantity() > book.getTotal_q())
                    return "Not enough" + i.getProduct_id();
            } else if (i.getProductType() == ProductType.PHONE) {
                Telephone telephone = telephoneService.getById(i.getProduct_id());
                if (i.getQuantity() > telephone.getTotal_q())
                    return "Not enough" + i.getProduct_id();
            } else if (i.getProductType() == ProductType.WAHSING_MASHINE) {
                WashingMachine washingMachine = washingMachineService.getById(i.getProduct_id());
                if (i.getQuantity() > washingMachine.getTotal_q())
                    return "Not enough" + i.getProduct_id();
            }
        }

        for (var i : cart.getProducts()) {
            if (i.getProductType() == ProductType.BOOK) {
                Book book = bookService.getById(i.getProduct_id());
                book.setTotal_q(book.getTotal_q() - i.getQuantity());
            } else if (i.getProductType() == ProductType.PHONE) {
                Telephone telephone = telephoneService.getById(i.getProduct_id());
                telephone.setTotal_q(telephone.getTotal_q() - i.getQuantity());
            } else if (i.getProductType() == ProductType.WAHSING_MASHINE) {
                WashingMachine washingMachine = washingMachineService.getById(i.getProduct_id());
                washingMachine.setTotal_q(washingMachine.getTotal_q() - i.getQuantity());
            }
            this.delete_product_in_cart(i.getId());
        }

        this.recalculateTotal(cart.getId());
        return "OK";
    }

}
