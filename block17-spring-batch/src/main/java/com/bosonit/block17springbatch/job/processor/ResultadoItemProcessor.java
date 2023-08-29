package com.bosonit.block17springbatch.job.processor;

import com.bosonit.block17springbatch.domain.Resultado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class ResultadoItemProcessor implements ItemProcessor<Resultado, Resultado> {
    private static final Logger log = LoggerFactory.getLogger(ResultadoItemProcessor.class);

    @Override
    public Resultado process(Resultado resultado) throws Exception {
        log.info("Calculando el riesgo según la temperatura media");
        if(resultado.getTempMedia() > 36) {
            resultado.setRiesgo("ALTO");
        } else if (resultado.getTempMedia() >= 32) {
            resultado.setRiesgo("MEDIO");
        } else {
            resultado.setRiesgo("BAJO");
        }
        return resultado;
    }
}