package com.example.pr5.controllers;

import com.example.pr5.models.Client;
import com.example.pr5.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService service;

    @PostMapping
    private ResponseEntity<Client> saveBook(@RequestBody Client object) {
        return ResponseEntity.ok(service.create(object));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    private List<Client> getAll() {
        return service.getAll();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    private Client getById(@PathVariable("id") int id) {
        return service.getById(id);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping
    private Client update(@RequestBody Client object) {
        service.create(object);
        return object;
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER', 'USER')")
    @DeleteMapping("/{id}")
    private void deleteBook(@PathVariable("id") int id) {
        service.delete(id);
    }
}