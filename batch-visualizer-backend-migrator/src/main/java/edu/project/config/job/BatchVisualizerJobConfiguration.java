package edu.project.config.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobFactory;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//https://yts.lt
//https://yts.lt/api
//cors

@Slf4j
@Configuration
public class BatchVisualizerJobConfiguration {
    private static final String JOB_NAME = "BatchVisualizerJob";
    private static final String STEP_NAME1 = "AprBatSchMstMigStep";
    private static final String STEP_NAME2 = "AprBatProLstMigStep";
    private static final String STEP_NAME3 = "AprPrfBatMstMigStep";
    private static final String STEP_NAME4 = "ComBatFileTrnMigStep";
    private static final String STEP_NAME5 = "ComBatResTrnMigStep";
    private static final String STEP_NAME6 = "BatDatFlgMstMigStep";
    private static final String STEP_NAME7 = "ComBatSchMstMigStep";
    private static final String STEP_NAME8 = "ComPrfBatMstMigStep";
    private static final String STEP_NAME9 = "ComBatExcHisMigStep";

    private final JobBuilderFactory jobBuilderFactory;

    @Autowired
    public BatchVisualizerJobConfiguration(JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean(JOB_NAME)
    public Job batchVisualizerJob(@Qualifier(STEP_NAME1) Step aprBatSchMstMigStep,
                                  @Qualifier(STEP_NAME2) Step aprBatProLstMigStep,
                                  @Qualifier(STEP_NAME3) Step aprPrfBatMstMigStep,
                                  @Qualifier(STEP_NAME4) Step comBatFileTrnMigStep,
                                  @Qualifier(STEP_NAME5) Step comBatResTrnMigStep,
                                  @Qualifier(STEP_NAME6) Step batDatFlgMstMigStep,
                                  @Qualifier(STEP_NAME7) Step comBatSchMstMigStep,
                                  @Qualifier(STEP_NAME8) Step comPrfBatMstMigStep,
                                  @Qualifier(STEP_NAME9) Step comBatExcHisMigStep) {
        log.info("job start>>>>");
        return jobBuilderFactory.get(JOB_NAME)
                .preventRestart()
                .start(aprBatSchMstMigStep)
                .next(aprBatProLstMigStep)
                .next(aprPrfBatMstMigStep)
                .next(comBatFileTrnMigStep)
                .next(comBatResTrnMigStep)
                .next(batDatFlgMstMigStep)
                .next(comBatSchMstMigStep)
                .next(comPrfBatMstMigStep)
                .next(comBatExcHisMigStep)
                    .on("*")
                    .end()
                .end()
                .build();
    }
}
