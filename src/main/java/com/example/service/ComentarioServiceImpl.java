package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.dao.ComentarioDao;
import com.example.entities.Comentario;

@Service
public class ComentarioServiceImpl implements ComentarioService{

    @Autowired
    private ComentarioDao comentarioDao;

    @Override
    public List<Comentario> findAllByPizza(long id) {
        // TODO Auto-generated method stub
        return comentarioDao.findAllByPizza(id);
    }

    @Override
    public Comentario findById(long id) {
        return comentarioDao.findById(id);
    }

    @Override
    public void delete(long id) {
        comentarioDao.deleteById(id);
        
    }

    @Override
    public Comentario save(Comentario comentario) {

        return comentarioDao.save(comentario);
    }
    
}
