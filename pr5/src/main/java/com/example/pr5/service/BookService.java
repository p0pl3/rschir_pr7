package com.example.pr5.service;

import com.example.pr5.models.Book;
import com.example.pr5.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepository repository;

    public Book create(Book object) {
        return repository.save(object);
    }

    public List<Book> getAll() {
        ArrayList<Book> object = new ArrayList<>();
        repository.findAll().forEach(object::add);
        return object;
    }

    public Book getById(int id) {
        return repository.findById(id).get();
    }

    public void update(Book object, int id) {
        repository.save(object);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}
