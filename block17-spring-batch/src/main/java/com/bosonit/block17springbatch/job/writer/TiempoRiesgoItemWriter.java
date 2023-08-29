package com.bosonit.block17springbatch.job.writer;

import com.bosonit.block17springbatch.domain.Tiempo;
import com.bosonit.block17springbatch.domain.TiempoRiesgo;
import com.bosonit.block17springbatch.repository.TiempoRepository;
import com.bosonit.block17springbatch.repository.TiempoRiesgoRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TiempoRiesgoItemWriter implements ItemWriter<TiempoRiesgo> {
    @Autowired
    TiempoRiesgoRepository tiempoRiesgoRepository;
    @Autowired
    TiempoRepository tiempoRepository;

    @Override
    public void write(Chunk<? extends TiempoRiesgo> chunk) throws Exception {
        chunk.forEach(tiempoRiesgo -> {
            Tiempo tiempo = tiempoRepository
                    .findById(tiempoRiesgo.getTiempo().getId())
                    .orElseThrow(() -> new RuntimeException("No existe el tiempo con id: " +
                            tiempoRiesgo.getTiempo().getId()));

            tiempoRepository.save(tiempo);
            tiempoRiesgoRepository.save(tiempoRiesgo);
        });
    }
}