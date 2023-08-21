package com.bosonit.examen_JPA_cascada.domain;
import com.bosonit.examen_JPA_cascada.domain.dto.FacturaInputDto;
import com.bosonit.examen_JPA_cascada.domain.dto.FacturaOutputDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CabeceraFra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    Cliente idCli;
    @Column
    double importeFra;
    @OneToMany(cascade = CascadeType.ALL)
    List<LineasFra> listaLineas;

    public CabeceraFra(FacturaInputDto facturaInputDto){
        this.importeFra = facturaInputDto.getImporteFra();
        this.listaLineas = new ArrayList<>();
    }

    public FacturaOutputDto facturaToFacturaDto(){
        return new FacturaOutputDto(
                this.id,
                this.idCli.clienteToClienteOutputDto(),
                this.importeFra,
                this.listaLineas.stream().map(LineasFra::lineaToLineaOutputDto).toList()
        );
    }

}
