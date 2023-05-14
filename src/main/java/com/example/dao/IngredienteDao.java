package com.example.dao;

import com.example.entities.Ingrediente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredienteDao extends JpaRepository<Ingrediente, Long>{
    
}
