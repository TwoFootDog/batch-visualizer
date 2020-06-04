package edu.project.config.step;

import edu.project.domain.ComBatExcHisVO;
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
public class ComBatExcHisMigStepConfiguration {

    private static final String STEP_NAME = "ComBatExcHisMigStep";
    private static final String BEAN_PREFIX = STEP_NAME + "_";

    private final StepBuilderFactory stepBuilderFactory;
    private final SqlSessionFactory batchDBSqlSessionFactory;
    private final SqlSessionFactory nxmileDBSqlSessionFactory;
    @Value("${chunkSize:1000}")
    private int chunkSize;
    private final Counter counter;  // micrometer 로 배치 수행 row수 체크를 위함

    @Autowired
    public ComBatExcHisMigStepConfiguration(StepBuilderFactory stepBuilderFactory,
                                            MeterRegistry meterRegistry,
                                            @Qualifier("batchDBSqlSessionFactory") SqlSessionFactory batchDBSqlSessionFactory,
                                            @Qualifier("nxmileDBSqlSessionFactory") SqlSessionFactory nxmileDBSqlSessionFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.batchDBSqlSessionFactory = batchDBSqlSessionFactory;
        this.nxmileDBSqlSessionFactory = nxmileDBSqlSessionFactory;
        this.counter = meterRegistry.counter("batch.counter", "stepName", "ComBatExcHis");
    }

    @Bean(STEP_NAME)
    public Step step(@Qualifier(BEAN_PREFIX + "reader") MyBatisPagingItemReader<ComBatExcHisVO> reader,
                     @Qualifier(BEAN_PREFIX + "processor") ItemProcessor<ComBatExcHisVO, ComBatExcHisVO> processor,
                     @Qualifier(BEAN_PREFIX + "writer") MyBatisBatchItemWriter<ComBatExcHisVO> writer) {
        return stepBuilderFactory.get(STEP_NAME)
                .<ComBatExcHisVO, ComBatExcHisVO>chunk(chunkSize)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @StepScope
    @Bean(BEAN_PREFIX + "reader")
    public MyBatisPagingItemReader<ComBatExcHisVO> reader() throws Exception {
        log.info(BEAN_PREFIX + "reader333366666666>>>>>>>>>>>>>");
        MyBatisPagingItemReader<ComBatExcHisVO> reader = new MyBatisPagingItemReader<>();
        reader.setSqlSessionFactory(nxmileDBSqlSessionFactory);
        reader.setPageSize(chunkSize);
        reader.setQueryId("edu.project.persistence.nxmileDB.NxmileDBMapper.getComBatExcHis");
        reader.afterPropertiesSet();
        return reader;
    }

    @StepScope
    @Bean(BEAN_PREFIX + "processor")
    public ItemProcessor<ComBatExcHisVO, ComBatExcHisVO> processor() throws Exception {
        return new ItemProcessor<ComBatExcHisVO, ComBatExcHisVO>() {
            @Override
            public ComBatExcHisVO process(ComBatExcHisVO item) throws Exception {
                log.info(BEAN_PREFIX + "processor3333455666666666>>>>>>>>>>>>");
                counter.increment(); // 배치 수행 건수 증가
                return item;
            }
        };
    }

    @StepScope
    @Bean(BEAN_PREFIX + "writer")
    public MyBatisBatchItemWriter<ComBatExcHisVO> writer() throws Exception {
        log.info(BEAN_PREFIX + "writer333366666666>>>>>>>>>>>>>");
        MyBatisBatchItemWriter<ComBatExcHisVO> writer = new MyBatisBatchItemWriter<>();
        writer.setSqlSessionFactory(batchDBSqlSessionFactory);
        writer.setStatementId("edu.project.persistence.batchDB.BatchDBMapper.setComBatExcHis");
        writer.afterPropertiesSet();
        return writer;
    }

}
