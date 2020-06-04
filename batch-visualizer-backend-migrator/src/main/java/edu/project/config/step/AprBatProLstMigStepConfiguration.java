package edu.project.config.step;

import edu.project.domain.AprBatProLstVO;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@Timed
public class AprBatProLstMigStepConfiguration {
    private final static String STEP_NAME = "AprBatProLstMigStep";
    private final static String BEAN_PREFIX = STEP_NAME + "_";

    private final StepBuilderFactory stepBuilderFactory;
    private final SqlSessionFactory batchDBSqlSessionFactory;
    private final SqlSessionFactory nxmileDBSqlSessionFactory;
    @Value("${chunksize:1000}")
    private int chunkSize;
    private final Counter counter;  // micrometer 로 배치 수행 row수 체크를 위함
    private final Timer timer;
    private long startTime;

    @Autowired
    public AprBatProLstMigStepConfiguration(StepBuilderFactory stepBuilderFactory,
                                            MeterRegistry meterRegistry,
                                            @Qualifier("batchDBSqlSessionFactory") SqlSessionFactory batchDBSqlSessionFactory,
                                            @Qualifier("nxmileDBSqlSessionFactory") SqlSessionFactory nxmileDBSqlSessionFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.batchDBSqlSessionFactory = batchDBSqlSessionFactory;
        this.nxmileDBSqlSessionFactory = nxmileDBSqlSessionFactory;
        this.counter = meterRegistry.counter("batch.counter", "stepName", "AprBatProLst");
        this.timer = meterRegistry.timer("batch.process.time", "stepName", "AprBatProLst");
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        startTime = System.currentTimeMillis();
        log.info("BeforeStep>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @AfterStep
    public void afterStep(StepExecution stepExecution) {
        long time =  System.currentTimeMillis() - startTime;
        timer.record(System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS);
        log.info(">>>>>>>>>>>>>>>>>>>>>After Step time>>>>>>>>>>>>>>>>>" + time);
    }

    @Bean(STEP_NAME)
    public Step step(@Qualifier(BEAN_PREFIX + "reader") MyBatisPagingItemReader<AprBatProLstVO> reader,
                     @Qualifier(BEAN_PREFIX + "processor") ItemProcessor<AprBatProLstVO, AprBatProLstVO> processor,
                     @Qualifier(BEAN_PREFIX + "writer")MyBatisBatchItemWriter<AprBatProLstVO> writer) throws Exception {
        startTime = System.currentTimeMillis();
        log.info(BEAN_PREFIX + "step>>>>>>>>>>>>>");
        return stepBuilderFactory.get(STEP_NAME)
                .<AprBatProLstVO, AprBatProLstVO>chunk(chunkSize)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @StepScope
    @Bean(BEAN_PREFIX + "reader")
    public MyBatisPagingItemReader<AprBatProLstVO> reader() throws Exception {
        log.info(BEAN_PREFIX + "reader>>>>>>>>>>>>>");
        MyBatisPagingItemReader<AprBatProLstVO> reader = new MyBatisPagingItemReader<>();
        reader.setSqlSessionFactory(nxmileDBSqlSessionFactory);
        reader.setPageSize(chunkSize);
        reader.setQueryId("edu.project.persistence.nxmileDB.NxmileDBMapper.getAllAprBatProLst");
        reader.afterPropertiesSet();
        return reader;
    }

    @StepScope
    @Bean(BEAN_PREFIX + "processor")
    public ItemProcessor<AprBatProLstVO, AprBatProLstVO> processor() throws Exception {
        return new ItemProcessor<AprBatProLstVO, AprBatProLstVO>() {
            @Override
            public AprBatProLstVO process(AprBatProLstVO item) throws Exception {
                log.info(BEAN_PREFIX + "processor>>>>>>>>>>>>>");
                counter.increment();    // 배치 수행 건수 증가
                return item;
            }
        };
    }

    @StepScope
    @Bean(BEAN_PREFIX + "writer")
    public MyBatisBatchItemWriter<AprBatProLstVO> writer() throws Exception {
        log.info(BEAN_PREFIX + "writer>>>>>>>>>>>>>");
        MyBatisBatchItemWriter<AprBatProLstVO> writer = new MyBatisBatchItemWriter<>();
        writer.setSqlSessionFactory(batchDBSqlSessionFactory);
        writer.setStatementId("edu.project.persistence.batchDB.BatchDBMapper.setAllAprBatProLst");
        writer.afterPropertiesSet();
        timer.record(System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS);
        return writer;
    }
}
