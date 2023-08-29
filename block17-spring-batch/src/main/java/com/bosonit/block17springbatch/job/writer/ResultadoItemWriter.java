package com.bosonit.block17springbatch.job.writer;

import com.bosonit.block17springbatch.domain.Resultado;
import com.bosonit.block17springbatch.repository.ResultadoRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

public class ResultadoItemWriter implements ItemWriter<Resultado> {
    @Autowired
    ResultadoRepository resultadoRepository;

    @Override
    public void write(Chunk<? extends Resultado> chunk) throws Exception {
        resultadoRepository.saveAll(chunk);
    }
}