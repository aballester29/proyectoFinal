package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.stream.events.Comment;

import com.example.dao.ComentarioDao;
import com.example.dao.IngredienteDao;
import com.example.dao.PizzaDao;
import com.example.dao.UsuarioDao;
import com.example.entities.Comentario;
import com.example.entities.Ingrediente;
import com.example.entities.Pizza;
import com.example.entities.Usuario;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PizzaServiceImpl implements PizzaService {

    @Autowired
    private PizzaDao pizzaDao;

    @Autowired
    private IngredienteDao ingredienteDao;

    @Autowired
    private ComentarioDao comentarioDao;

    @Override
    public List<Pizza> findAll(Sort sort) {
        return pizzaDao.findAll(sort);
    }

    @Override
    public Page<Pizza> findAll(Pageable pageable) {
        return pizzaDao.findAll(pageable);
    }

    @Override
    public Pizza findById(long id) {
        return pizzaDao.findById(id);
    }

    @Override
    public void delete(long id) {
        Pizza pizza = pizzaDao.findById(id);

        for(Comentario coment : pizza.getComentarios()){
            comentarioDao.delete(coment);
        }
        pizzaDao.deleteById(id);

    }

    @Override
    public Pizza save(Pizza pizza) {
        List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
        Double precio = 0.0;
        for (Ingrediente ing : pizza.getIngredientes()) {
            Long id = ing.getId();
            Ingrediente ingrediente = ingredienteDao.findById(id).get();
            if (ingrediente != null) {
                ingredientes.add(ingrediente);
                precio += ingrediente.getPrecio();
            }
        }
        precio = precio * 1.2;
        pizza.setPrecio(precio);
        pizza.setIngredientes(ingredientes);
        return pizzaDao.save(pizza);
    }

}
