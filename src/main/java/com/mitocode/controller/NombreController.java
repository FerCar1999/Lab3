package com.mitocode.controller;

import com.mitocode.dto.NombreDTO;
import com.mitocode.dto.PosicionDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Nombre;
import com.mitocode.model.Posicion;
import com.mitocode.service.INombreService;
import com.mitocode.service.IPosionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/nombres")
public class NombreController {
    @Autowired
    private INombreService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<NombreDTO>> findAll(){
        List<NombreDTO> list = service.findAll().stream().map(p->mapper.map(p,NombreDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NombreDTO> findById(@PathVariable("id") Integer id){
        NombreDTO dtoresp;
        Nombre obj = service.findById(id);
        if (obj==null){
            throw new ModelNotFoundException("ID NO ENCONTRADO: "+id);
        }else{
            dtoresp = mapper.map(obj, NombreDTO.class);
        }
        return new ResponseEntity<>(dtoresp, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody NombreDTO dto) {
        Nombre p = service.save(mapper.map(dto, Nombre.class));
        //localhost:8080/patients/3
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getIdNombre()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Nombre> update(@Valid @RequestBody NombreDTO dto) {
        Nombre obj = service.findById(dto.getIdNombre());
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + dto.getIdNombre());
        }

        return new ResponseEntity<>(service.update(mapper.map(dto, Nombre.class)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        Nombre obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            service.delete(id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
