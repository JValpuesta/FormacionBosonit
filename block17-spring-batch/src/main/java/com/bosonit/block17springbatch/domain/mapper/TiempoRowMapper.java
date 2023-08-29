package com.bosonit.block17springbatch.domain.mapper;

import com.bosonit.block17springbatch.domain.Tiempo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TiempoRowMapper implements RowMapper<Tiempo> {
    @Override
    public Tiempo mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tiempo tiempo = new Tiempo();
        tiempo.setId(rs.getInt("id"));
        tiempo.setLocalidad(rs.getString("localidad"));
        tiempo.setTemperatura(rs.getInt("temperatura"));
        tiempo.setFecha(rs.getDate("fecha"));

        return tiempo;
    }
}