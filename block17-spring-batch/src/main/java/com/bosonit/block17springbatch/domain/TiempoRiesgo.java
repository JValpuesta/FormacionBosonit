package com.bosonit.block17springbatch.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TiempoRiesgo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTiempoRiesgo;
    @OneToOne
    @JoinColumn(name = "tiempo_id")
    private Tiempo tiempo;
    private Date fechaPrediccion;
    private String riesgo;

}
