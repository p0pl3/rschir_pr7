package com.example.pr5.service;

import com.example.pr5.models.Cart;
import com.example.pr5.models.Client;
import com.example.pr5.repositories.CartRepository;
import com.example.pr5.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.ref.Cleaner;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CartRepository cartRepository;

    public Client create(Client client) {
        Client clientt = clientRepository.save(client);
        Cart cart = new Cart();
        cart.setClient(clientt);
        cart.setTotal(0);
        cartRepository.save(cart);
        return clientt;
    }

    public List<Client> getAll() {
        ArrayList<Client> Clients = new ArrayList<>();
        clientRepository.findAll().forEach(Clients::add);
        return Clients;
    }

    public Client getById(int id) {
        return clientRepository.findById(id).get();
    }

    public void update(Client clients, int Clientid) {
        clientRepository.save(clients);
    }

    public void delete(int id) {
        clientRepository.deleteById(id);
    }
}
