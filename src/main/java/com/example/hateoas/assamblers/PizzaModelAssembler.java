package com.example.hateoas.assamblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.controllers.PizzaRESTController;
import com.example.entities.Pizza;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PizzaModelAssembler implements RepresentationModelAssembler<Pizza, EntityModel<Pizza>> {

    @Override
    public EntityModel<Pizza> toModel(Pizza producto) {
        return EntityModel.of(producto, // 
                        linkTo(methodOn(PizzaRESTController.class).findById(producto.getId())).withSelfRel(),
                        linkTo(methodOn(PizzaRESTController.class).findAll(1,3)).withRel("pizza"));
    }
    
}