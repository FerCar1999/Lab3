package com.mitocode.dto;

import com.mitocode.model.Camisa;
import com.mitocode.model.Posicion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NombreDTO {
    @EqualsAndHashCode.Include
    private Integer idNombre;

    private String nombre;

    private CamisaDTO camisa;

    private PosicionDTO posicion;
}
