package com.example.service;

import java.util.List;

import com.example.entities.Ingrediente;

public interface IngredienteService {
    public List<Ingrediente> findAll();
    public Ingrediente findById(long id);
    public void save(Ingrediente ingrediente);
}
