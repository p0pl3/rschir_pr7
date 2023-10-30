package com.example.pr5.service;

import com.example.pr5.models.WashingMachine;
import com.example.pr5.repositories.WahingMachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WashingMachineService {
    @Autowired
    WahingMachineRepository repository;

    public WashingMachine create(WashingMachine object) {
        return repository.save(object);
    }

    public List<WashingMachine> getAll() {
        ArrayList<WashingMachine> object = new ArrayList<>();
        repository.findAll().forEach(object::add);
        return object;
    }

    public WashingMachine getById(int id) {
        return repository.findById(id).get();
    }

    public void update(WashingMachine object, int id) {
        repository.save(object);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}
