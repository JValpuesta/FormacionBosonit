package com.bosonit.block17springbatch.domain.mapper;

import com.bosonit.block17springbatch.domain.Resultado;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultadoRowMapper implements RowMapper<Resultado> {
    @Override
    public Resultado mapRow(ResultSet rs, int rowNum) throws SQLException {
        Resultado resultado = new Resultado();
        resultado.setIdResultado(rowNum);
        resultado.setLocalidad(rs.getString("localidad"));
        resultado.setMes(rs.getInt("mes"));
        resultado.setAnyo(rs.getInt("anyo"));
        resultado.setNumMediciones(rs.getInt("num_mediciones"));
        resultado.setTempMedia(rs.getFloat("temp_media"));

        return resultado;
    }
}