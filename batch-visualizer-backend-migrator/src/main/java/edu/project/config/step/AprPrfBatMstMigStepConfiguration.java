package edu.project.config.step;

import edu.project.domain.AprPrfBatMstVO;
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
public class AprPrfBatMstMigStepConfiguration {
    private static final String STEP_NAME = "AprPrfBatMstMigStep";
    private static final String BEAN_PREFIX = STEP_NAME + "_";

    private final StepBuilderFactory stepBuilderFactory;
    private final SqlSessionFactory batchDBSqlSessionFactory;
    private final SqlSessionFactory nxmildDBSqlSessionFactory;
    @Value("${chunkSize:1000}")
    private int chunkSize;
    private final Counter counter;  // micrometer 로 배치 수행 row수 체크를 위함

    @Autowired
    public AprPrfBatMstMigStepConfiguration(StepBuilderFactory stepBuilderFactory,
                                            MeterRegistry meterRegistry,
                                            @Qualifier("batchDBSqlSessionFactory") SqlSessionFactory batchDBSqlSessionFactory,
                                            @Qualifier("nxmileDBSqlSessionFactory") SqlSessionFactory nxmildDBSqlSessionFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.batchDBSqlSessionFactory = batchDBSqlSessionFactory;
        this.nxmildDBSqlSessionFactory = nxmildDBSqlSessionFactory;
        this.counter = meterRegistry.counter("batch.counter", "stepName", "AprPrfBatMst");
    }

    @Bean(STEP_NAME)
    public Step step(@Qualifier(BEAN_PREFIX + "reader") MyBatisPagingItemReader<AprPrfBatMstVO> reader,
                     @Qualifier(BEAN_PREFIX + "processor") ItemProcessor<AprPrfBatMstVO, AprPrfBatMstVO> processor,
                     @Qualifier(BEAN_PREFIX + "writer") MyBatisBatchItemWriter<AprPrfBatMstVO> writer) throws Exception {
        return stepBuilderFactory.get(STEP_NAME)
                .<AprPrfBatMstVO, AprPrfBatMstVO>chunk(chunkSize)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @StepScope
    @Bean(BEAN_PREFIX + "reader")
    public MyBatisPagingItemReader<AprPrfBatMstVO> reader() throws Exception {
        log.info(BEAN_PREFIX + "reader>>>>>>>>>>>>>");
        MyBatisPagingItemReader<AprPrfBatMstVO> reader = new MyBatisPagingItemReader<>();
        reader.setPageSize(chunkSize);
        reader.setSqlSessionFactory(nxmildDBSqlSessionFactory);
        reader.setQueryId("edu.project.persistence.nxmileDB.NxmileDBMapper.getAllAprPrfBatMst");
        reader.afterPropertiesSet();
        return reader;
    }

    @StepScope
    @Bean(BEAN_PREFIX + "processor")
    public ItemProcessor<AprPrfBatMstVO, AprPrfBatMstVO> processor() throws Exception {
        return new ItemProcessor<AprPrfBatMstVO, AprPrfBatMstVO>() {
            @Override
            public AprPrfBatMstVO process(AprPrfBatMstVO item) throws Exception {
                log.info(BEAN_PREFIX + "processor>>>>>>>>>>>>>");
                counter.increment(); // 배치 수행 건수 증가
                return item;
            }
        };
    }

    @StepScope
    @Bean(BEAN_PREFIX + "writer")
    public MyBatisBatchItemWriter<AprPrfBatMstVO> writer() throws Exception {
        log.info(BEAN_PREFIX + "writer>>>>>>>>>>>>>");
        MyBatisBatchItemWriter<AprPrfBatMstVO> writer = new MyBatisBatchItemWriter<>();
        writer.setSqlSessionFactory(batchDBSqlSessionFactory);
        writer.setStatementId("edu.project.persistence.batchDB.BatchDBMapper.setAllAprPrfBatMst");
        writer.afterPropertiesSet();
        return writer;
    }


}
