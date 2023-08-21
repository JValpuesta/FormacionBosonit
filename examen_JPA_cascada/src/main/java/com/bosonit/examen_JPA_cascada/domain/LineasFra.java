package com.bosonit.examen_JPA_cascada.domain;

import com.bosonit.examen_JPA_cascada.domain.dto.FacturaInputDto;
import com.bosonit.examen_JPA_cascada.domain.dto.LineaInputDto;
import com.bosonit.examen_JPA_cascada.domain.dto.LineaOutputDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LineasFra {

    @Id
    @GeneratedValue
    Integer id;
    @ManyToOne
    @JoinColumn(name = "id_factura")
    CabeceraFra idFra;
    @Column(name = "nombre_del_producto", nullable = false)
    String proNomb;
    @Column
    double cantidad;
    @Column
    double precio;

    public LineasFra(LineaInputDto lineaInputDto){
        this.proNomb = lineaInputDto.getProducto();
        this.cantidad = lineaInputDto.getCantidad();
        this.precio = lineaInputDto.getImporte();
    }

    public LineaOutputDto lineaToLineaOutputDto() {
        return new LineaOutputDto(this.id, this.proNomb, this.cantidad, this.precio);
    }

}
