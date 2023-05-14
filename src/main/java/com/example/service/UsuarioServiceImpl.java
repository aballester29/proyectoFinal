package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.UsuarioDao;
import com.example.entities.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    public Usuario findById(long id) {
        // TODO Auto-generated method stub
        return usuarioDao.findById(id).get();
    }

    @Override
    public Usuario save(Usuario user) {
        // TODO Auto-generated method stub
        return usuarioDao.save(user);
    }
    
}
