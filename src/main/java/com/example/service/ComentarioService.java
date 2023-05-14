package com.example.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.entities.Comentario;

public interface ComentarioService {
    public List<Comentario> findAllByPizza(long id);
	public Comentario findById(long id);
	public void delete(long id);
	public Comentario save(Comentario comentario);
}
