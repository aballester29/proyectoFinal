package com.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.context.annotation.Bean;

import com.example.entities.Comentario;
import com.example.entities.Ingrediente;
import com.example.entities.Pizza;
import com.example.entities.Usuario;
import com.example.service.ComentarioService;
import com.example.service.IngredienteService;
import com.example.service.PizzaService;
import com.example.service.UsuarioService;

@SpringBootApplication
public class ProyectoFinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoFinalApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoData(PizzaService pizzaService, IngredienteService ingredienteService, ComentarioService comentarioService, UsuarioService usuarioService) {
		return args -> { 
			//Insertamos ingredientes
			ingredienteService.save(Ingrediente.builder().nombre("Queso").precio(1.5).build());
			ingredienteService.save(Ingrediente.builder().nombre("Aceitunas").precio(1.7).build());
			ingredienteService.save(Ingrediente.builder().nombre("Pepperoni").precio(1.8).build());
			ingredienteService.save(Ingrediente.builder().nombre("Piña").precio(2.0).build());
			ingredienteService.save(Ingrediente.builder().nombre("Chocolate").precio(1.3).build());
			ingredienteService.save(Ingrediente.builder().nombre("Salsa BBQ").precio(1.2).build());


			List<Ingrediente> ingMargarita = new ArrayList<>();
			List<Ingrediente> ingBarbacoa = new ArrayList<>();
			List<Ingrediente> ingOreo = new ArrayList<>();
			List<Ingrediente> ingPeperoni = new ArrayList<>();
			List<Ingrediente> ingHawaiana = new ArrayList<>();
			List<Ingrediente> ingAceitunas = new ArrayList<>();
			ingMargarita.add(ingredienteService.findById(1L));
			ingBarbacoa.add(ingredienteService.findById(6L));
			ingOreo.add(ingredienteService.findById(5L));
			ingPeperoni.add(ingredienteService.findById(1L));
			ingPeperoni.add(ingredienteService.findById(3L));
			ingHawaiana.add(ingredienteService.findById(1L));
			ingHawaiana.add(ingredienteService.findById(4L));
			ingAceitunas.add(ingredienteService.findById(1L));
			ingAceitunas.add(ingredienteService.findById(2L));
			ingAceitunas.add(ingredienteService.findById(3L));

			//Insertamos las pizzas
			pizzaService.save(Pizza.builder().nombre("Margarita").foto("pizza-margarita.jpg").ingredientes(ingMargarita).build());
			pizzaService.save(Pizza.builder().nombre("Barbacoa").foto("pizza-barbacoa.jpg").ingredientes(ingBarbacoa).build());
			pizzaService.save(Pizza.builder().nombre("Oreo").foto("pizza-oreo.jpg").ingredientes(ingOreo).build());
			pizzaService.save(Pizza.builder().nombre("Peperoni").foto("pizza-con-chorizo-jamon-y-queso.jpg").ingredientes(ingPeperoni).build());
			pizzaService.save(Pizza.builder().nombre("Hawaiana").foto("pizza-piña.jpg").ingredientes(ingHawaiana).build());
			pizzaService.save(Pizza.builder().nombre("Aceitunas").foto("pizzerías.jpeg").ingredientes(ingAceitunas).build());

			//Insertamos un usuario
			usuarioService.save(Usuario.builder().nombre("Usuario1").apellidos("user1").email("user1@user.com").password("usuario").build());

			//Insertamos comentarios
			comentarioService.save(Comentario.builder().texto("Muy buena pizza").puntuacion(8.0).pizza(pizzaService.findById(1L)).build());
			comentarioService.save(Comentario.builder().texto("La mejor pizza de mi vida").puntuacion(10.0).pizza(pizzaService.findById(1L)).build());
			comentarioService.save(Comentario.builder().texto("Una basura").puntuacion(2.0).pizza(pizzaService.findById(5L)).build());
			comentarioService.save(Comentario.builder().texto("Me ha encantado").puntuacion(7.0).pizza(pizzaService.findById(2L)).build());
			
		};
	}

}
