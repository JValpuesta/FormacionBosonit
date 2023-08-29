package com.bosonit.block17springbatch.configuration;

import com.bosonit.block17springbatch.domain.Resultado;
import com.bosonit.block17springbatch.domain.Tiempo;
import com.bosonit.block17springbatch.domain.TiempoRiesgo;
import com.bosonit.block17springbatch.domain.mapper.TiempoRiesgoRowMapper;
import com.bosonit.block17springbatch.domain.mapper.TiempoRowMapper;
import com.bosonit.block17springbatch.job.processor.ResultadoItemProcessor;
import com.bosonit.block17springbatch.job.processor.TiempoItemProcessor;
import com.bosonit.block17springbatch.job.processor.TiempoRiesgoItemProcessor;
import com.bosonit.block17springbatch.repository.TiempoRepository;
import com.bosonit.block17springbatch.job.writer.ResultadoItemWriter;
import com.bosonit.block17springbatch.job.writer.TiempoItemWriter;
import com.bosonit.block17springbatch.job.writer.TiempoRiesgoItemWriter;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.batch.core.Step;
import org.springframework.transaction.PlatformTransactionManager;


import javax.sql.DataSource;

@Configuration
@AllArgsConstructor
public class BatchConfig {

    @Autowired
    DataSource dataSource;
    private static final Logger log = LoggerFactory.getLogger(BatchConfig.class);

    @Bean
    public FlatFileItemReader<Tiempo> tiempoReader() {
        log.info("--> Iniciando lectura de registros desde el archivo prueba.csv");
        return new FlatFileItemReaderBuilder<Tiempo>()
                .name("tiempoReader")
                .resource(new ClassPathResource("prueba.csv"))
                .linesToSkip(1)
                .delimited()
                .names("localidad", "temperatura", "fecha")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Tiempo>() {{
                    setTargetType(Tiempo.class);
                }})
                .build();
    }

    @Bean
    public JdbcCursorItemReader<Tiempo> tiempoReaderDataBase(){
        log.info("--> Lectura de objetos Tiempo de la base de datos");
        JdbcCursorItemReader<Tiempo> reader = new JdbcCursorItemReader<>();
        reader.setSql("SELECT * FROM tiempo");
        reader.setDataSource(dataSource);
        reader.setFetchSize(100);
        reader.setRowMapper(new TiempoRowMapper());

        return reader;
    }

    @Bean
    public JdbcCursorItemReader<TiempoRiesgo> tiempoRiesgoItemReader() {
        log.info("--> Lectura de objetos Tiempo-Riesgo de la base de datos");
        JdbcCursorItemReader<TiempoRiesgo> reader = new JdbcCursorItemReader<>();
        reader.setSql("SELECT * FROM tiempo_riesgo");
        reader.setDataSource(dataSource);
        reader.setFetchSize(100);
        reader.setRowMapper(new TiempoRiesgoRowMapper());

        return reader;
    }

    @Bean
    public JdbcCursorItemReader<Resultado> resultadoItemReader() {
        log.info("--> Lectura de objetos Resultado de la base de datos. Cálculo de la tª media y nº de mediciones");
        JdbcCursorItemReader<Resultado> cursorItemReader = new JdbcCursorItemReader<>();
        cursorItemReader.setDataSource(dataSource);
        cursorItemReader.setSql(
                "SELECT "
                        + "t.localidad AS localidad, "
                        + "MONTH(tr.fecha_prediccion) AS mes, "
                        + "YEAR(tr.fecha_prediccion) AS anyo, "
                        + "COUNT(*) AS num_mediciones, "
                        + "AVG(t.temperatura) AS temp_media, "
                        + "FROM tiempo_riesgo tr, tiempo t "
                        + "WHERE tr.id_Tiempo_Riesgo = t.id "
                        + "GROUP BY localidad, mes, anyo "
                        + "ORDER BY localidad ASC, mes ASC"
        );

        cursorItemReader.setRowMapper(new BeanPropertyRowMapper<>(Resultado.class));

        return cursorItemReader;
    }


    //Processors
    @Bean
    public ItemProcessor<Tiempo, Tiempo> tiempoItemProcessor() {
        return new TiempoItemProcessor();
    }

    @Bean
    public ItemProcessor<Tiempo, TiempoRiesgo> tiempoRiesgoItemProcessor(){
        return new TiempoRiesgoItemProcessor();
    }

    @Bean
    public ItemProcessor<Resultado, Resultado> resultadoItemProcessor(){
        return new ResultadoItemProcessor();
    }

    //Writers
    @Bean
    public ItemWriter<Tiempo> tiempoItemWriter(TiempoRepository tiempoRepository) {
        return new TiempoItemWriter(tiempoRepository);
    }

    @Bean
    public ItemWriter<TiempoRiesgo> tiempoRiesgoItemWriter() { return new TiempoRiesgoItemWriter(); }

    @Bean
    public ItemWriter<Resultado> resultadoItemWriter() {
        return new ResultadoItemWriter();
    }

    //Listener
    @Bean
    public JobExecutionListener listener() {
        return new JobListener();
    }


    //Step
    @Bean
    public Step stepTiempoCsv(TiempoRepository tiempoRepository, JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)
                .<Tiempo, Tiempo>chunk(5, transactionManager)
                .reader(tiempoReader())
                .processor(tiempoItemProcessor())
                .writer(new TiempoItemWriter(tiempoRepository))
                .listener(listener())
                .build();
    }

    @Bean
    public Step stepRiesgoTiempo(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step2", jobRepository)
                .<Tiempo, TiempoRiesgo>chunk(5, transactionManager)
                .reader(tiempoReaderDataBase())
                .processor(tiempoRiesgoItemProcessor())
                .writer(tiempoRiesgoItemWriter())
                .build();
    }

    @Bean
    public Step stepResultados(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step3", jobRepository)
                .<Resultado, Resultado>chunk(5, transactionManager)
                .reader(resultadoItemReader())
                .processor(resultadoItemProcessor())
                .writer(resultadoItemWriter())
                .build();
    }

    //Job
    @Bean
    public Job jobCsvTiempo(TiempoRepository tiempoRepository, JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        System.out.println("- Este es el JOB -");
        return new JobBuilder("jobCsvTiempo", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(stepTiempoCsv(tiempoRepository, jobRepository, transactionManager))
                .next(stepRiesgoTiempo(jobRepository, transactionManager))
                .next(stepResultados(jobRepository, transactionManager))
                .end()
                .build();
    }
}
