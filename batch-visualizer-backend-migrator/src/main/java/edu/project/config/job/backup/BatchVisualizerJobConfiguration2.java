package edu.project.config.job.backup;//package edu.project.config.job;
//
//import edu.project.domain.BatDatFlgMstVO;
//import edu.project.domain.SysConMstVO;
//import edu.project.domain.SysInfMstVO;
//import edu.project.share.DataShareBean;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.batch.MyBatisBatchItemWriter;
//import org.mybatis.spring.batch.MyBatisPagingItemReader;
//import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
//import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.StepExecution;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.JobScope;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.core.job.flow.FlowExecutionStatus;
//import org.springframework.batch.core.job.flow.JobExecutionDecider;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.convert.converter.Converter;
//
//import javax.annotation.Resource;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
////@RequiredArgsConstructor
//@Slf4j
////@Configuration
//public class BatchVisualizerJobConfiguration {
//    private static final String JOB_NAME = "BatchVisualizer";
//    private static final String BEAN_PREFIX = JOB_NAME + "_";
//    private static int sysInfMstVOIndex = 0;
//    private static int deciderIndex = 0;
//
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//    private final SqlSessionFactory batchDBSqlSessionFactory;
//    private final SqlSessionFactory nxmileDBSqlSessionFactory;
//
//    @Autowired
//    public BatchVisualizerJobConfiguration(JobBuilderFactory jobBuilderFactory,
//                                           StepBuilderFactory stepBuilderFactory,
//                                           @Qualifier("batchDBSqlSessionFactory") SqlSessionFactory batchDBSqlSessionFactory,
//                                           @Qualifier("nxmileDBSqlSessionFactory") SqlSessionFactory nxmileDBSqlSessionFactory) {
//        this.jobBuilderFactory = jobBuilderFactory;
//        this.stepBuilderFactory = stepBuilderFactory;
//        this.batchDBSqlSessionFactory = batchDBSqlSessionFactory;
//        this.nxmileDBSqlSessionFactory = nxmileDBSqlSessionFactory;
//    }
//
///*    @Resource(name = "batchDBSqlSessionFactory")
//    private final SqlSessionFactory batchDBSqlSessionFactory;
//    @Resource(name = "nxmileDBSqlSessionFactory")
//    private final SqlSessionFactory nxmileDBSqlSessionFactory;*/
//
//    @Value("${chunkSize:1000}")
//    private int chunkSize;
//
//    @Bean(JOB_NAME)
//    public Job batchVisualizerJob(@Qualifier(BEAN_PREFIX+"step") Step batchVisualizerStep,
//                                  @Qualifier(BEAN_PREFIX+"step2") Step batchVisualizerStep2,
//                                  @Qualifier("dataShareBean") DataShareBean<SysInfMstVO> dataShareBean,
//                                  @Qualifier("Decider") JobExecutionDecider decider) {
//        dataShareBean.clearData();
//        log.info("job sysInfMstVOIndex >>>>>>>> : {}" ,sysInfMstVOIndex);
////        return jobBuilderFactory.get(JOB_NAME).start(batchVisualizerStep).build();
//        return jobBuilderFactory.get(JOB_NAME)
//                .preventRestart()
//                .start(batchVisualizerStep)
//                .next(decider)
//                .on("CONTINUE").to(batchVisualizerStep2)
//                .on("COMPLETE").end()
//                .from(batchVisualizerStep2).next(decider).on("CONTINUE").to(batchVisualizerStep2)
//                .from(batchVisualizerStep2).next(decider).on("COMPLETE").end()
//                .end()
//                .build();
//    }
//
//    @Bean(BEAN_PREFIX + "step")
//    public Step batchVisualizerJStep(@Qualifier(BEAN_PREFIX+"reader") MyBatisPagingItemReader<SysInfMstVO> reader,
//                                     @Qualifier(BEAN_PREFIX+"writer") ItemWriter<SysInfMstVO> writer) {
//        return stepBuilderFactory.get(BEAN_PREFIX + "step")
//                .<SysInfMstVO, SysInfMstVO>chunk(chunkSize)
//                .reader(reader)
//                .processor(processor())
//                .writer(writer)
//                .build();
//    }
//
//    @Bean(BEAN_PREFIX + "step2")
//    public Step batchVisualizerJStep2(@Qualifier(BEAN_PREFIX+"reader2") MyBatisPagingItemReader<BatDatFlgMstVO> reader,
//                                      @Qualifier(BEAN_PREFIX+"processor2") ItemProcessor<BatDatFlgMstVO, BatDatFlgMstVO> processor,
//                                      @Qualifier(BEAN_PREFIX+"writer2") MyBatisBatchItemWriter<BatDatFlgMstVO> writer) {
//        return stepBuilderFactory.get(BEAN_PREFIX + "step2")
//                .<BatDatFlgMstVO, BatDatFlgMstVO>chunk(chunkSize)
//                .reader(reader)
//                .processor(processor2())
//                .writer(writer)
//                .build();
//    }
//
//    @StepScope
//    @Bean(BEAN_PREFIX + "reader")
//    public MyBatisPagingItemReader<SysInfMstVO> reader() {
//        log.info("reader>>>>>>>>>>>>>");
//        return new MyBatisPagingItemReaderBuilder<SysInfMstVO>()
//                .sqlSessionFactory(batchDBSqlSessionFactory)
//                .pageSize(chunkSize)
//                .queryId("edu.project.persistence.batchDB.SysInfMstMapper.getAllSysInfMst")
//                .build();
//    }
//
//    @StepScope
//    @Bean(BEAN_PREFIX + "processor")
//    public ItemProcessor<SysInfMstVO, SysInfMstVO> processor() {
//        return new ItemProcessor<SysInfMstVO, SysInfMstVO>() {
//            @Override
//            public SysInfMstVO process(SysInfMstVO item) throws Exception {
//                log.info("processor1>>>>>>>>>>>>>>>>");
//                return item;
//            }
//        };
//    }
//
//    @StepScope
//    @Bean(BEAN_PREFIX + "writer")
//    public ItemWriter<SysInfMstVO> writer(@Qualifier("dataShareBean") DataShareBean<SysInfMstVO> dataShareBean) {
//        return new ItemWriter<SysInfMstVO>() {
//            @Override
//            public void write(List<? extends SysInfMstVO> items) throws Exception {
//                dataShareBean.putData("SysInfMstVO", (List<SysInfMstVO>) items);
//                List<SysInfMstVO> sysInfMstVO = dataShareBean.getData("SysInfMstVO");
//                log.info("List>>>>>>>>>>>>>>>>> : {}", sysInfMstVO);
//            }
//        };
//    }
//
//    @StepScope
//    @Bean(BEAN_PREFIX + "reader2")
//    public MyBatisPagingItemReader<BatDatFlgMstVO> reader2(@Qualifier("dataShareBean") DataShareBean<SysInfMstVO> dataShareBean,
//                                                           @Value("#{@readerParameters2}") Map<String, Object> readerParameters2) {
////                                                        @Qualifier("readerParameters2") Map<String, Object> readerParameters2) {
//
//        log.info("reader2>>>>>> : {}", dataShareBean.getData("SysInfMstVO").get(sysInfMstVOIndex));
//
//        MyBatisPagingItemReader<BatDatFlgMstVO> myBatisPagingItemReader = new MyBatisPagingItemReader<>();
//        myBatisPagingItemReader.setSqlSessionFactory(nxmileDBSqlSessionFactory);
//        myBatisPagingItemReader.setParameterValues(readerParameters2);
//        myBatisPagingItemReader.setQueryId("edu.project.persistence.nxmileDB.BatDatFlgMstMapper.getAllBatDatFlgMst");
////        myBatisPagingItemReader.setQueryId("edu.project.persistence.batchDB.SysConMstMapper.getSysConMst");
//        myBatisPagingItemReader.setPageSize(chunkSize);
//        return myBatisPagingItemReader;
//    }
//
//    @StepScope
//    @Bean
//    public Map<String, Object> readerParameters2(@Qualifier("dataShareBean") DataShareBean<SysInfMstVO> dataShareBean) {
//        Map<String, Object> map = new HashMap<>();
//        log.info("sysInfMstVOIndex>>>>>>>>>>> : {}", sysInfMstVOIndex);
//        map.put("sysId", dataShareBean.getData("SysInfMstVO").get(sysInfMstVOIndex).getSysId());
//        log.info("reader2parameter>>>>>>>>>>>>>>>>> : {}", dataShareBean.getData("SysInfMstVO").get(sysInfMstVOIndex).getSysId());
//        return map;
//    }
//
//    @Bean(BEAN_PREFIX + "processor2")
//    public ItemProcessor<BatDatFlgMstVO, BatDatFlgMstVO> processor2() {
//        return new ItemProcessor<BatDatFlgMstVO, BatDatFlgMstVO>() {
//            @Override
//            public BatDatFlgMstVO process(BatDatFlgMstVO item) throws Exception {
//                log.info("processor2>>>>>>>>>>>>>>> item : {}", item);
//                return item;
//            }
//        };
//    }
//    @StepScope
//    @Bean(BEAN_PREFIX + "writer2")
//    public MyBatisBatchItemWriter<BatDatFlgMstVO> writer2(@Qualifier("dataShareBean") DataShareBean<SysInfMstVO> dataShareBean) {
//        log.info("writer2>>>>>>>>");
//        MyBatisBatchItemWriter<BatDatFlgMstVO> myBatisBatchItemWriter = new MyBatisBatchItemWriter<>();
//        myBatisBatchItemWriter.setSqlSessionFactory(batchDBSqlSessionFactory);
//        myBatisBatchItemWriter.setStatementId("edu.project.persistence.batchDB.BatVisMstMapper.setBatVisMst");
//        myBatisBatchItemWriter.setItemToParameterConverter(createItemToParameterMapConverter(dataShareBean.getData("SysInfMstVO").get(sysInfMstVOIndex++).getSysId()));
//        return myBatisBatchItemWriter;
///*        return new MyBatisBatchItemWriterBuilder<BatDatFlgMstVO>()
//                .sqlSessionFactory(batchDBSqlSessionFactory)
//                .statementId("edu.project.persistence.batchDB.BatVisMstMapper.setBatVisMst")
//                .itemToParameterConverter(createItemToParameterMapConverter(dataShareBean.getData("SysInfMstVO").get(sysInfMstVOIndex++).getSysId()))
//                .build();*/
//    }
//
//    public static <BatDatFlgMstVO> Converter<BatDatFlgMstVO, Map<String, Object>> createItemToParameterMapConverter(String sysId) {
//        return new Converter<BatDatFlgMstVO, Map<String, Object>>() {
//            @Override
//            public Map<String, Object> convert(BatDatFlgMstVO items) {
//                Map<String, Object> parameter = new HashMap<>();
//                parameter.put("items", items);
//                parameter.put("sysId", sysId);
//                return parameter;
//            }
//        };
//    }
//
//    @Bean("Decider")
//    public JobExecutionDecider decider() {
//        return new NextDecider();
//    }
//    public static class NextDecider implements JobExecutionDecider {
//
//        @Setter(onMethod_ = {@Autowired})
//        @Qualifier("dataShareBean")
//        private DataShareBean<SysInfMstVO> dataShareBean;
//
//        @Override
//        public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
//            if (dataShareBean.getData("SysInfMstVO").size() > deciderIndex) {
//                log.info("continue>>>>>>>>>>>>>>>>>>>>>>>>>>> {} {}", dataShareBean.getData("SysInfMstVO").get(deciderIndex));
//                deciderIndex++;
//                return new FlowExecutionStatus("CONTINUE");
//            } else {
//                log.info("complete>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//                sysInfMstVOIndex = 0;
//                deciderIndex = 0;
//                return new FlowExecutionStatus("COMPLETE");
//            }
//        }
//    }
//}
