package com.bosonit.block17springbatch.configuration;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobListener implements JobExecutionListener {
    private static int errorCounter = 0;
    private static final int MAX_ERROR_COUNT = 100;
    @Override
    public void beforeJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.STARTED){
            System.out.println("Job started");
        }
    }
    @Override
    public void afterJob(JobExecution jobExecution){
        if(errorCounter >= MAX_ERROR_COUNT) {
            jobExecution.setStatus(BatchStatus.FAILED);
            System.out.println("Job failed. Se han detectado 100 o más errores en los registros.");
        } else if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            System.out.println("Job ended");
        }
    }

    public static int getErrorCounter(){
        return errorCounter;
    }

    public static void incrementErrorCounter(){
        errorCounter = getErrorCounter() + 1;
    }
}