package com.mitocode.controller;

import com.mitocode.dto.PatientDTO;
import com.mitocode.dto.PosicionDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Patient;
import com.mitocode.model.Posicion;
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
@RequestMapping("/posiciones")
public class PosicionController {
    @Autowired
    private IPosionService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<PosicionDTO>> findAll(){
        List<PosicionDTO> list = service.findAll().stream().map(p->mapper.map(p,PosicionDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PosicionDTO> findById(@PathVariable("id") Integer id){
        PosicionDTO dtoresp;
        Posicion obj = service.findById(id);
        if (obj==null){
            throw new ModelNotFoundException("ID NO ENCONTRADO: "+id);
        }else{
            dtoresp = mapper.map(obj, PosicionDTO.class);
        }
        return new ResponseEntity<>(dtoresp, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody PosicionDTO dto) {
        Posicion p = service.save(mapper.map(dto, Posicion.class));
        //localhost:8080/patients/3
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getIdPosicion()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Posicion> update(@Valid @RequestBody PosicionDTO dto) {
        Posicion obj = service.findById(dto.getIdPosicion());
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + dto.getIdPosicion());
        }

        return new ResponseEntity<>(service.update(mapper.map(dto, Posicion.class)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        Posicion obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            service.delete(id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
