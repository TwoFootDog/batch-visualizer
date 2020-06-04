package edu.project.config.step;

import edu.project.domain.ComBatResTrnVO;
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
public class ComBatResTrnMigStepConfiguration {

    private static final String STEP_NAME = "ComBatResTrnMigStep";
    private static final String BEAN_PREFIX = STEP_NAME + "_";

    private final StepBuilderFactory stepBuilderFactory;
    private final SqlSessionFactory batchDBSqlSessionFactory;
    private final SqlSessionFactory nxmileDBSqlSessionFactory;
    @Value("${chunkSize:1000}")
    private int chunkSize;
    private final Counter counter;  // micrometer 로 배치 수행 row수 체크를 위함

    @Autowired
    public ComBatResTrnMigStepConfiguration(StepBuilderFactory stepBuilderFactory,
                                            MeterRegistry meterRegistry,
                                            @Qualifier("batchDBSqlSessionFactory") SqlSessionFactory batchDBSqlSessionFactory,
                                            @Qualifier("nxmileDBSqlSessionFactory") SqlSessionFactory nxmileDBSqlSessionFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.batchDBSqlSessionFactory = batchDBSqlSessionFactory;
        this.nxmileDBSqlSessionFactory = nxmileDBSqlSessionFactory;
        this.counter = meterRegistry.counter("batch.counter", "stepName", "ComBatResTrn");
    }

    @Bean(STEP_NAME)
    public Step step(@Qualifier(BEAN_PREFIX + "reader") MyBatisPagingItemReader<ComBatResTrnVO> reader,
                     @Qualifier(BEAN_PREFIX + "processor") ItemProcessor<ComBatResTrnVO, ComBatResTrnVO> processor,
                     @Qualifier(BEAN_PREFIX + "writer") MyBatisBatchItemWriter<ComBatResTrnVO> writer) {
        return stepBuilderFactory.get(STEP_NAME)
                .<ComBatResTrnVO, ComBatResTrnVO>chunk(chunkSize)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @StepScope
    @Bean(BEAN_PREFIX + "reader")
    public MyBatisPagingItemReader<ComBatResTrnVO> reader() throws Exception {
        log.info(BEAN_PREFIX + "reader>>>>>>>>>>>>>");
        MyBatisPagingItemReader<ComBatResTrnVO> reader = new MyBatisPagingItemReader<>();
        reader.setSqlSessionFactory(nxmileDBSqlSessionFactory);
        reader.setPageSize(chunkSize);
        reader.setQueryId("edu.project.persistence.nxmileDB.NxmileDBMapper.getComBatResTrn");
        reader.afterPropertiesSet();
        return reader;
    }

    @StepScope
    @Bean(BEAN_PREFIX + "processor")
    public ItemProcessor<ComBatResTrnVO, ComBatResTrnVO> processor() throws Exception {
        return new ItemProcessor<ComBatResTrnVO, ComBatResTrnVO>() {
            @Override
            public ComBatResTrnVO process(ComBatResTrnVO item) throws Exception {
                log.info(BEAN_PREFIX + "processor>>>>>>>>>>>>>");
                counter.increment(); // 배치 수행 건수 증가
                return item;
            }
        };
    }

    @StepScope
    @Bean(BEAN_PREFIX + "writer")
    public MyBatisBatchItemWriter<ComBatResTrnVO> writer() throws Exception {
        log.info(BEAN_PREFIX + "writer>>>>>>>>>>>>>");
        MyBatisBatchItemWriter<ComBatResTrnVO> writer = new MyBatisBatchItemWriter<>();
        writer.setSqlSessionFactory(batchDBSqlSessionFactory);
        writer.setStatementId("edu.project.persistence.batchDB.BatchDBMapper.setComBatResTrn");
        writer.afterPropertiesSet();
        return writer;
    }

}
