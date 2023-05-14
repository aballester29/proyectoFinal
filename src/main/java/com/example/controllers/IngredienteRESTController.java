package com.example.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Ingrediente;
import com.example.hateoas.assamblers.PizzaModelAssembler;
import com.example.service.IngredienteService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ingredientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE })
public class IngredienteRESTController {
    @Autowired
    IngredienteService ingredienteService;


    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Ingrediente>>> findAll() {

        Map<Long, Ingrediente> ingredientesMap = new HashMap<>();
        List<Ingrediente> ingredientes = null;
        ingredientes = ingredienteService.findAll();

        for (var i = 0; i < ingredientes.size(); i++) {
            ingredientesMap.put(ingredientes.get(i).getId(), ingredientes.get(i));
        }

        List<Ingrediente> ingredientesCollection = ingredientesMap.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());

       
        return ResponseEntity.ok( StreamSupport.stream(ingredientesCollection.spliterator(), false)
        .map(this::toModel)
        .collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of))); 

    }


    public EntityModel<Ingrediente> toModel(Ingrediente producto) {
        return EntityModel.of(producto);
                        
    }
}
