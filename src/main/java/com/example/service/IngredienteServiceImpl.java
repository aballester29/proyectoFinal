package com.example.service;

import java.util.List;

import com.example.dao.IngredienteDao;
import com.example.dao.PizzaDao;
import com.example.entities.Ingrediente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredienteServiceImpl implements IngredienteService{

    @Autowired
    PizzaDao pizzaDao;
    
    @Autowired
    IngredienteDao ingredienteDao;
    @Override
    public List<Ingrediente> findAll() {
        // TODO Auto-generated method stub
        return ingredienteDao.findAll();
    }

    @Override
    public Ingrediente findById(long id) {
        // TODO Auto-generated method stub
        return ingredienteDao.findById(id).get();
    }

    @Override
    public void save(Ingrediente ingrediente) {
        ingredienteDao.save(ingrediente);
        
    }
    
}
