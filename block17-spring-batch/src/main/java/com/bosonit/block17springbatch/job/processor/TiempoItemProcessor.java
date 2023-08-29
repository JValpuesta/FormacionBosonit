package com.bosonit.block17springbatch.job.processor;

import com.bosonit.block17springbatch.domain.Tiempo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class TiempoItemProcessor implements ItemProcessor<Tiempo, Tiempo> {
    private static final Logger log = LoggerFactory.getLogger(TiempoItemProcessor.class);
    @Override
    public Tiempo process(Tiempo item) throws Exception {
        log.info("Procesando registro de tiempo");
        Tiempo tiempo = new Tiempo();
        tiempo.setLocalidad(item.getLocalidad());
        tiempo.setTemperatura(item.getTemperatura());
        tiempo.setFecha(item.getFecha());
        return tiempo;
    }
}