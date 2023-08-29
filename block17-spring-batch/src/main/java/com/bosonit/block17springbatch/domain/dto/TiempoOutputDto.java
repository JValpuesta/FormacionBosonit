package com.bosonit.block17springbatch.domain.dto;

import com.bosonit.block17springbatch.domain.Tiempo;
import lombok.*;

import java.text.SimpleDateFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TiempoOutputDto {

    private String ciudad;
    private String fecha;
    private Integer temperatura;

    public TiempoOutputDto(Tiempo tiempo) {
        this.ciudad = tiempo.getLocalidad();
        this.fecha = new SimpleDateFormat("dd/MM/yyyy").format(tiempo.getFecha());
        this.temperatura = tiempo.getTemperatura();
    }

}
