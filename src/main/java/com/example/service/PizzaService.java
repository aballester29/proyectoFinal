package com.example.service;

import java.util.List;

import com.example.entities.Pizza;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


public interface PizzaService {
    public List<Pizza> findAll(Sort sort);
	public Page<Pizza> findAll(Pageable pageable);
	public Pizza findById(long id);
	public void delete(long id);
	public Pizza save(Pizza producto);
}
