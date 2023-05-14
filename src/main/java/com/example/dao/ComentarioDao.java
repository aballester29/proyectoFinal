package com.example.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entities.Comentario;

@Repository
public interface ComentarioDao extends JpaRepository<Comentario, Long>{
    // Recupera un listado de pizzas ordenado
    @Query(value = "select c from Comentario c where c.pizza.id = :id")
    public List<Comentario> findAllByPizza(long id);

    // Recupera la pizza que busquemos
    @Query(value = "select c from Comentario c where c.id = :id")
    public Comentario findById(long id);
}
