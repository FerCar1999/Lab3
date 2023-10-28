package com.mitocode.controller;

import com.mitocode.dto.CamisaDTO;
import com.mitocode.dto.PosicionDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Camisa;
import com.mitocode.model.Posicion;
import com.mitocode.service.ICamisaService;
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
@RequestMapping("/camisas")
public class CamisaController {
    @Autowired
    private ICamisaService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<CamisaDTO>> findAll(){
        List<CamisaDTO> list = service.findAll().stream().map(p->mapper.map(p,CamisaDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CamisaDTO> findById(@PathVariable("id") Integer id){
        CamisaDTO dtoresp;
        Camisa obj = service.findById(id);
        if (obj==null){
            throw new ModelNotFoundException("ID NO ENCONTRADO: "+id);
        }else{
            dtoresp = mapper.map(obj, CamisaDTO.class);
        }
        return new ResponseEntity<>(dtoresp, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody CamisaDTO dto) {
        Camisa p = service.save(mapper.map(dto, Camisa.class));
        //localhost:8080/patients/3
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getIdCamisa()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Camisa> update(@Valid @RequestBody CamisaDTO dto) {
        Camisa obj = service.findById(dto.getIdCamisa());
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + dto.getIdCamisa());
        }

        return new ResponseEntity<>(service.update(mapper.map(dto, Camisa.class)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        Camisa obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            service.delete(id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
