package edu.project.config.step;

import edu.project.domain.ComBatFileTrnVO;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ComBatFileTrnMigStepConfiguration {

    private static final String STEP_NAME = "ComBatFileTrnMigStep";
    private static final String BEAN_PREFIX = STEP_NAME + "_";

    private final StepBuilderFactory stepBuilderFactory;
    private final SqlSessionFactory batchDBSqlSessionFactory;
    private final SqlSessionFactory nxmileDBSqlSessionFactory;
    @Value("${chunkSize:1000}")
    private int chunkSize;
    private final Counter counter;  // micrometer 로 배치 수행 row수 체크를 위함

    @Autowired
    public ComBatFileTrnMigStepConfiguration(StepBuilderFactory stepBuilderFactory,
                                             MeterRegistry meterRegistry,
                                             @Qualifier("batchDBSqlSessionFactory") SqlSessionFactory batchDBSqlSessionFactory,
                                             @Qualifier("nxmileDBSqlSessionFactory") SqlSessionFactory nxmileDBSqlSessionFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.batchDBSqlSessionFactory = batchDBSqlSessionFactory;
        this.nxmileDBSqlSessionFactory = nxmileDBSqlSessionFactory;
        this.counter = meterRegistry.counter("batch.counter", "stepName", "ComBatFileTrn");
    }

    @Bean(STEP_NAME)
    public Step step(@Qualifier(BEAN_PREFIX + "reader") MyBatisPagingItemReader<ComBatFileTrnVO> reader,
                     @Qualifier(BEAN_PREFIX + "processor") ItemProcessor<ComBatFileTrnVO, ComBatFileTrnVO> processor,
                     @Qualifier(BEAN_PREFIX + "writer")MyBatisBatchItemWriter<ComBatFileTrnVO> writer) throws Exception {
        return stepBuilderFactory.get(STEP_NAME)
                .<ComBatFileTrnVO, ComBatFileTrnVO>chunk(chunkSize)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @StepScope
    @Bean(BEAN_PREFIX + "reader")
    public MyBatisPagingItemReader<ComBatFileTrnVO> reader() throws Exception {
        log.info(BEAN_PREFIX + "reader>>>>>>>>>>>>>");
        MyBatisPagingItemReader<ComBatFileTrnVO> reader = new MyBatisPagingItemReader<>();
        reader.setSqlSessionFactory(nxmileDBSqlSessionFactory);
        reader.setPageSize(chunkSize);
        reader.setQueryId("edu.project.persistence.nxmileDB.NxmileDBMapper.getComBatFileTrn");
        reader.afterPropertiesSet();
        return reader;
    }

    @StepScope
    @Bean(BEAN_PREFIX + "processor")
    public ItemProcessor<ComBatFileTrnVO, ComBatFileTrnVO> processor() throws Exception {
        return new ItemProcessor<ComBatFileTrnVO, ComBatFileTrnVO>() {
            @Override
            public ComBatFileTrnVO process(ComBatFileTrnVO item) throws Exception {
                log.info(BEAN_PREFIX + "processor>>>>>>>>>>>>>");
                counter.increment(); // 배치 수행 건수 증가
                return item;
            }
        };
    }

    @StepScope
    @Bean(BEAN_PREFIX + "writer")
    public MyBatisBatchItemWriter<ComBatFileTrnVO> writer() throws Exception {
        log.info(BEAN_PREFIX + "writer>>>>>>>>>>>>>");
        MyBatisBatchItemWriter<ComBatFileTrnVO> writer = new MyBatisBatchItemWriter<>();
        writer.setSqlSessionFactory(batchDBSqlSessionFactory);
        writer.setStatementId("edu.project.persistence.batchDB.BatchDBMapper.setComBatFileTrn");
        writer.afterPropertiesSet();
        return writer;
    }
}
