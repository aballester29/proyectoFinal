package com.example.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.UsuarioDao;
import com.example.entities.Comentario;
import com.example.entities.Pizza;
import com.example.entities.Usuario;
import com.example.hateoas.assamblers.PizzaModelAssembler;
import com.example.service.ComentarioService;
import com.example.service.PizzaService;
import com.example.service.UsuarioService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pizzas")
@RequiredArgsConstructor
public class PizzaRESTController {
	@Autowired
	PizzaService pizzaService;

	@Autowired
	ComentarioService comentarioService;

	@Autowired
	UsuarioService usuarioService;

	@NonNull
	private final PizzaModelAssembler assembler;

	@GetMapping
	public CollectionModel<EntityModel<Pizza>> findAll(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) {

		// Para que devuelva el listado de productos ordenado por nombre, tanto si es
		// con paginacion
		// como si no lo es
		Sort sortByName = Sort.by("nombre");
		Sort sortById = Sort.by("id");

		List<Pizza> pizzas = null;
		List<Comentario> comentarios = null;

		if (page != null && size != null) {
			// Con paginacion
			Pageable pageablePizza = PageRequest.of(page, size, sortByName);
			pizzas = pizzaService.findAll(pageablePizza).getContent();

			for (Pizza pizza : pizzas) {
				comentarios = comentarioService.findAllByPizza(pizza.getId());
				pizza.setComentarios(comentarios);
			}

		} else {
			// Sin paginacion y devolvemos la lista completa de los productos
			pizzas = pizzaService.findAll(sortById);
			for (Pizza pizza : pizzas) {
				comentarios = comentarioService.findAllByPizza(pizza.getId());
				if (comentarios != null) {
					pizza.setComentarios(comentarios);
				}
			}
		}

		var productosWithHyperlinks = pizzas.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(productosWithHyperlinks,
				linkTo(methodOn(PizzaRESTController.class).findAll(page, size)).withSelfRel());

	}

	@GetMapping(value = "/{id}")
	public EntityModel<Pizza> findById(@PathVariable(name = "id") long id) {

		Pizza producto = pizzaService.findById(id);

		return assembler.toModel(producto);
	}

	@DeleteMapping
	public ResponseEntity<List<Pizza>> delete(@RequestParam(required = false) Long id) {

		pizzaService.delete(id);
		Sort sortByName = Sort.by("id");

		// ------------MODIFICAR PARA QUE DEVUELVA UN MENSAJE DE PRODUCTO ---------------
		// BORRADO------------------
		ResponseEntity<List<Pizza>> responseEntity = null;

		List<Pizza> pizzas = null;

		pizzas = pizzaService.findAll(sortByName);

		if (pizzas.size() > 0) {
			responseEntity = new ResponseEntity<List<Pizza>>(pizzas, HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<List<Pizza>>(HttpStatus.NO_CONTENT);
		}

		return responseEntity;
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody Pizza pizza, BindingResult result) {

		ResponseEntity<Map<String, Object>> responseEntity = null;

		Map<String, Object> responseAsMap = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errores = new ArrayList<>();

			for (ObjectError error : result.getAllErrors()) {
				errores.add(error.getDefaultMessage());
			}

			responseAsMap.put("Errores", errores);
			responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

		} else {
			try {
				Pizza productodb = pizzaService.save(pizza);

				if (productodb != null) {
					for(Comentario coment : productodb.getComentarios()){
						coment.setPizza(productodb);
						coment.setUsuario(usuarioService.findById(1L));
						comentarioService.save(coment);
					}
					responseAsMap.put("Guardado correctamente", pizza);
					responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);
				} else {
					responseAsMap.put("Errores", "No se ha guardado el producto");
					responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
				}

			} catch (DataAccessException e) {
				responseAsMap.put("Errores", "No existe la presentacion");
				responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
			}
		}

		return responseEntity;

	}

	@PutMapping
	public ResponseEntity<Map<String, Object>> modify(@Valid @RequestBody Pizza pizza, BindingResult result) {

		ResponseEntity<Map<String, Object>> responseEntity = null;

		Map<String, Object> responseAsMap = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errores = new ArrayList<>();

			for (ObjectError error : result.getAllErrors()) {
				errores.add(error.getDefaultMessage());
			}

			responseAsMap.put("Errores", errores);
			responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

		} else {
			try {
				Pizza productodb = pizzaService.save(pizza);

				if (productodb != null) {
					for(Comentario coment : productodb.getComentarios()){
						coment.setPizza(productodb);
						coment.setUsuario(usuarioService.findById(1L));
						comentarioService.save(coment);
					}
					responseAsMap.put("Modificado correctamente", pizza);
					responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);
				} else {
					responseAsMap.put("Errores", "No se ha guardado el producto");
					responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
				}

			} catch (DataAccessException e) {
				responseAsMap.put("Errores", "No existe la presentacion");
				responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
			}
		}
		return responseEntity;

	}
}
