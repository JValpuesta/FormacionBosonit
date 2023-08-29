package com.bosonit.block17springbatch.job.writer;

import com.bosonit.block17springbatch.domain.Tiempo;
import com.bosonit.block17springbatch.repository.TiempoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TiempoItemWriter implements ItemWriter<Tiempo> {

    @Autowired
    private final TiempoRepository tiempoRepository;

    private boolean isJobFailed = false;
    private static final Logger log = LoggerFactory.getLogger(TiempoItemWriter.class);
    private List<Tiempo> chunkErroneo = new ArrayList<>();
    private static int erroresEnChunk = 0;
    private static int errorCounter = 0;

    public TiempoItemWriter(TiempoRepository tiempoRepository) {
        this.tiempoRepository = tiempoRepository;
    }

    @Override
    public void write(Chunk<? extends Tiempo> chunk) throws Exception {
        if (!isJobFailed) {
            log.info("_____ Registros recibidos de tiempo: {} _____", chunk.size());
            List<Tiempo> registrosErroneos = new ArrayList<>();

            chunk.forEach(tiempo -> {
                if (tiempo.getTemperatura() >= 50 || tiempo.getTemperatura() <= -20) {
                    log.info("** Error detectado ** Tiempo con temperatura inválida: {}", tiempo);
                    registrosErroneos.add(tiempo);
                    chunkErroneo.add(tiempo);
                    errorCounter++;
                    erroresEnChunk++;

                } else {
                    log.info("Registro con temperatura válida, se guarda en la base de datos: {}", tiempo);
                    chunkErroneo.clear();
                    tiempoRepository.save(tiempo);
                    erroresEnChunk = 0;
                }

                if (erroresEnChunk == 5) {
                    try {
                        log.info("*** Chunk erróneo detectado ***");
                        writeToErrorCSVChunk(chunkErroneo);
                        chunkErroneo.clear();
                        erroresEnChunk = 0;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            if (errorCounter >= 100) {
                log.info("*** Se han detectado 100 o más errores en los registros ***");
                tiempoRepository.deleteAll();
                isJobFailed = true;
            }

            if (!registrosErroneos.isEmpty()) {
                writeToCSV(registrosErroneos);
            }
        }
    }

    private void writeToCSV(List<Tiempo> registrosErroneos) throws IOException {

        log.info("Escribiendo registros erróneos en el archivo CSV");

        String fileName = "REGISTROS_ERRORES.csv";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));

        registrosErroneos.forEach(tiempo -> {
            try {
                String line = String.format("%s,%s,%s",
                        tiempo.getLocalidad(),
                        tiempo.getFecha(),
                        tiempo.getTemperatura());
                writer.write(line);
                writer.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        writer.close();
    }

    private void writeToErrorCSVChunk(List<Tiempo> registrosErroneos) throws IOException {
        log.info("Escribiendo chunk despreciado con 5 registros erróneos en el archivo CSV de chunks erróneos");
        String fileName = "CHUNKS_ERRONEOS.csv";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));

        for (Tiempo tiempo : registrosErroneos) {
            try {
                String line = String.format("%s,%s,%s",
                        tiempo.getLocalidad(),
                        tiempo.getFecha(),
                        tiempo.getTemperatura());
                writer.write(line);
                writer.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        writer.close();
    }

}
