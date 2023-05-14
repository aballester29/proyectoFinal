package com.example.dao;

import java.util.List;

import com.example.entities.Pizza;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface PizzaDao extends JpaRepository<Pizza, Long>{
    
    // Recupera un listado de pizzas ordenado
    @Query(value = "select p from Pizza p left join fetch p.comentarios")
    public List<Pizza> findAll(Sort sort);

    // Recupera un listado de pizzas que se puede paginar (Pageable)
    // Pageable ya incluye ordenamiento
    @Query(value = "select p from Pizza p left join fetch p.comentarios", countQuery = "select count(p) from Pizza p left join p.comentarios")
    public Page<Pizza> findAll(Pageable pageable);

    // Recupera la pizza que busquemos
    @Query(value = "select p from Pizza p left join fetch p.comentarios where p.id = :id")
    public Pizza findById(long id);
}
