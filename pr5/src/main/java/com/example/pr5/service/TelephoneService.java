package com.example.pr5.service;

import com.example.pr5.models.Telephone;
import com.example.pr5.repositories.TelephoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TelephoneService {
    @Autowired
    TelephoneRepository repository;

    public Telephone create(Telephone object) {
        return repository.save(object);
    }

    public List<Telephone> getAll() {
        ArrayList<Telephone> object = new ArrayList<>();
        repository.findAll().forEach(object::add);
        return object;
    }

    public Telephone getById(int id) {
        return repository.findById(id).get();
    }

    public void update(Telephone object, int id) {
        repository.save(object);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}

