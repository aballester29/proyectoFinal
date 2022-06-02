package com.example.service;

import com.example.entities.Usuario;

public interface UsuarioService {
    public Usuario findById(long id);
    public Usuario save(Usuario user);
}
