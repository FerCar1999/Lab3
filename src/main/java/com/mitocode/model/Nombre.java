package com.mitocode.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
public class Nombre {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idNombre;

    @Column(nullable = false, length = 255)
    private String nombre;

    @ManyToOne
    @JoinColumn(name="idCamisa", nullable = false, foreignKey = @ForeignKey(name="FK_CAMISA_NOMBRE"))
    private Camisa camisa;

    @ManyToOne
    @JoinColumn(name="idPosicion", nullable = false, foreignKey = @ForeignKey(name="FK_POSICION_NOMBRE"))
    private Posicion posicion;
}
