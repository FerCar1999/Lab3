package com.mitocode.service.impl;

import com.mitocode.model.Camisa;
import com.mitocode.model.Nombre;
import com.mitocode.repo.ICamisaRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.INombreRepo;
import com.mitocode.service.ICamisaService;
import com.mitocode.service.INombreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NombreServiceImpl extends CRUDImpl<Nombre, Integer> implements INombreService {
    @Autowired
    private INombreRepo repo;

    @Override
    protected IGenericRepo<Nombre, Integer> getRepo(){return repo;}
}