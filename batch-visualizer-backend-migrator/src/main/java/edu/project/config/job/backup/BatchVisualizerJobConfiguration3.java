//package edu.project.config.job.backup;
//
//import edu.project.share.DataShareBean;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.batch.MyBatisBatchItemWriter;
//import org.mybatis.spring.batch.MyBatisPagingItemReader;
//import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.convert.converter.Converter;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
////https://yts.lt
////https://yts.lt/api
////cors
//
////@RequiredArgsConstructor
//@Slf4j
//@Configuration
//public class BatchVisualizerJobConfiguration3 {
//    private static final String JOB_NAME = "BatchVisualizer";
//    private static final String BEAN_PREFIX = JOB_NAME + "_";
//    private static int mapIndex = 0;
//    private static int deciderIndex = 0;
//
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//    private final SqlSessionFactory batchDBSqlSessionFactory;
//    private final SqlSessionFactory nxmileDBSqlSessionFactory;
//
//    @Autowired
//    public BatchVisualizerJobConfiguration3(JobBuilderFactory jobBuilderFactory,
//                                            StepBuilderFactory stepBuilderFactory,
//                                            @Qualifier("batchDBSqlSessionFactory") SqlSessionFactory batchDBSqlSessionFactory,
//                                            @Qualifier("nxmileDBSqlSessionFactory") SqlSessionFactory nxmileDBSqlSessionFactory) {
//        this.jobBuilderFactory = jobBuilderFactory;
//        this.stepBuilderFactory = stepBuilderFactory;
//        this.batchDBSqlSessionFactory = batchDBSqlSessionFactory;
//        this.nxmileDBSqlSessionFactory = nxmileDBSqlSessionFactory;
//    }
//
//    @Value("${chunkSize:1000}")
//    private int chunkSize;
//
//
//    //ALTER TABLE TBL_MAP_MST ADD MAP_SEQ BIGINT;
//    @Bean(JOB_NAME)
//    public Job batchVisualizerJob(@Qualifier(BEAN_PREFIX+"step") Step batchVisualizerStep,
//                                  @Qualifier(BEAN_PREFIX+"step2") Step batchVisualizerStep2) {
//        log.info("job mapIndex >>>>>>>> : {}" ,mapIndex);
//        return jobBuilderFactory.get(JOB_NAME)
//                .preventRestart()
//                .start(batchVisualizerStep)
//                .next(batchVisualizerStep2)
//                .build();
//    }
//
//    @Bean(BEAN_PREFIX + "step")
//    public Step batchVisualizerJStep(@Qualifier(BEAN_PREFIX+"reader") MyBatisPagingItemReader<GetMappingTableOutVO> reader,
//                                     @Qualifier(BEAN_PREFIX+"writer") ItemWriter<GetMappingTableOutVO> writer) {
//        return stepBuilderFactory.get(BEAN_PREFIX + "step")
//                .<GetMappingTableOutVO, GetMappingTableOutVO>chunk(chunkSize)
//                .reader(reader)
//                .processor(processor())
//                .writer(writer)
//                .build();
//    }
//
//    @StepScope
//    @Bean(BEAN_PREFIX + "reader")
//    public MyBatisPagingItemReader<GetMappingTableOutVO> reader() {
//        log.info("reader>>>>>>>>>>>>>");
//        return new MyBatisPagingItemReaderBuilder<GetMappingTableOutVO>()
//                .sqlSessionFactory(batchDBSqlSessionFactory)
//                .pageSize(chunkSize)
//                .queryId("edu.project.persistence.batchDB.TblMapMstMapper.getMappingTable")
//                .build();
//    }
//
//    @StepScope
//    @Bean(BEAN_PREFIX + "processor")
//    public ItemProcessor<GetMappingTableOutVO, GetMappingTableOutVO> processor() {
//        return new ItemProcessor<GetMappingTableOutVO, GetMappingTableOutVO>() {
//            @Override
//            public GetMappingTableOutVO process(GetMappingTableOutVO item) throws Exception {
//                log.info("processor1>>>>>>>>>>>>>>>>");
//                return item;
//            }
//        };
//    }
//
//    @StepScope
//    @Bean(BEAN_PREFIX + "writer")
//    public ItemWriter<GetMappingTableOutVO> writer(@Qualifier("dataShareBean") DataShareBean<GetMappingTableOutVO> dataShareBean) {
//        return new ItemWriter<GetMappingTableOutVO>() {
//            @Override
//            public void write(List<? extends GetMappingTableOutVO> items) throws Exception {
//                dataShareBean.putData("GetMappingTableOutVO", (List<GetMappingTableOutVO>) items);
//                List<GetMappingTableOutVO> GetMappingTableOutVO = dataShareBean.getData("GetMappingTableOutVO");
//                log.info("List>>>>>>>>>>>>>>>>> : {}", GetMappingTableOutVO);
//            }
//        };
//    }
//
//    @Bean(BEAN_PREFIX + "step2")
//    public Step batchVisualizerJStep2(@Qualifier(BEAN_PREFIX+"reader2") MyBatisPagingItemReader<BatVisMstVO> reader,
//                                      @Qualifier(BEAN_PREFIX+"processor2") ItemProcessor<BatVisMstVO, BatVisMstVO> processor,
//                                      @Qualifier(BEAN_PREFIX+"writer2") MyBatisBatchItemWriter<BatVisMstVO> writer) {
//        return stepBuilderFactory.get(BEAN_PREFIX + "step2")
//                .<BatVisMstVO, BatVisMstVO>chunk(chunkSize)
//                .reader(reader)
//                .processor(processor)
//                .writer(writer)
//                .build();
//    }
//    @StepScope
//    @Bean(BEAN_PREFIX + "reader2")
//    public MyBatisPagingItemReader<BatVisMstVO> reader2(@Qualifier("dataShareBean") DataShareBean<GetMappingTableOutVO> dataShareBean,
//                                                                    @Value("#{@readerParameters2}") Map<String, Object> readerParameters2) {
//
//        log.info("reader2>>>>>> : {}", dataShareBean.getData("GetMappingTableOutVO").get(mapIndex));
//
//        MyBatisPagingItemReader<BatVisMstVO> myBatisPagingItemReader = new MyBatisPagingItemReader<>();
//        myBatisPagingItemReader.setSqlSessionFactory(nxmileDBSqlSessionFactory);
//        myBatisPagingItemReader.setParameterValues(readerParameters2);
//        myBatisPagingItemReader.setQueryId("edu.project.persistence.nxmileDB.BatDatFlgMstMapper.getAllBatDatFlgMst");
//        myBatisPagingItemReader.setPageSize(chunkSize);
//        return myBatisPagingItemReader;
//    }
//
//    @StepScope
//    @Bean
//    public Map<String, Object> readerParameters2(@Qualifier("dataShareBean") DataShareBean<GetMappingTableOutVO> dataShareBean) {
//        Map<String, Object> map = new HashMap<>();
//        log.info("mapIndex>>>>>>>>>>> : {}", mapIndex);
//        map.put("qry1", dataShareBean.getData("GetMappingTableOutVO").get(mapIndex).getQry1());
//        return map;
//    }
//
//    @Bean(BEAN_PREFIX + "processor2")
//    public ItemProcessor<BatVisMstVO, BatVisMstVO> processor2(@Qualifier("dataShareBean") DataShareBean<GetMappingTableOutVO> dataShareBean) {
//        return new ItemProcessor<BatVisMstVO, BatVisMstVO>() {
//            @Override
//            public BatVisMstVO process(BatVisMstVO item) throws Exception {
////                int index = 0;
////                String qry1 = "INSERT INTO " +
////                        dataShareBean.getData("GetMappingTableOutVO").get(mapIndex).getTgtTblNm() + "(CONN_ID,";
////                String qry2 = "VALUES (\"" + dataShareBean.getData("GetMappingTableOutVO").get(mapIndex).getConnId() + "\",";
////
////                while(Integer.parseInt(dataShareBean.getData("GetMappingTableOutVO").get(mapIndex).getMaxMapSeq()) > index) {
////                    if (index == 0) {
////                        qry1 = qry1 + dataShareBean.getData("GetMappingTableOutVO").get(mapIndex).getTgtCol1();
////                        qry2 = qry2 + "\"" + item.getSrcCol1() + "\"";
////                    } else if (index == 1) {
////                        qry1 = qry1 + dataShareBean.getData("GetMappingTableOutVO").get(mapIndex).getTgtCol2();
////                        qry2 = qry2 + "\"" + item.getSrcCol2()  + "\"";
////                    } else if (index == 2) {
////                        qry1 = qry1 + dataShareBean.getData("GetMappingTableOutVO").get(mapIndex).getTgtCol3();
////                        qry2 = qry2 + "\"" + item.getSrcCol3()  + "\"";
////                    } else if (index == 3) {
////                        qry1 = qry1 + dataShareBean.getData("GetMappingTableOutVO").get(mapIndex).getTgtCol4();
////                        qry2 = qry2 + "\"" + item.getSrcCol4()  + "\"";
////                    }
////
////                    index ++;
////
////                    if (Integer.parseInt(dataShareBean.getData("GetMappingTableOutVO").get(mapIndex).getMaxMapSeq()) > index) {
////                        qry1 =  qry1 + ",";
////                        qry2 = qry2 + ",";
////                    } else {
////                        qry1 = qry1 + ") ";
////                        qry2 = qry2 + ");";
////                    }
////                }
//                log.info("processor2>>>>>>>>>>>>>>> item : {}", item);
//                return item;
////
////                SysConMstVO sysConMstVO = new SysConMstVO(qry1+qry2);
////                log.info("processor2>>>>>>>>>>>>>>> sysConMstVO : {}", sysConMstVO);
////                return sysConMstVO;
//
//            }
//        };
//    }
//    @StepScope
//    @Bean(BEAN_PREFIX + "writer2")
//    public MyBatisBatchItemWriter<BatVisMstVO> writer2(@Qualifier("dataShareBean") DataShareBean<GetMappingTableOutVO> dataShareBean) {
//        log.info("writer2>>>>>>>>");
//        MyBatisBatchItemWriter<BatVisMstVO> myBatisBatchItemWriter = new MyBatisBatchItemWriter<>();
//        myBatisBatchItemWriter.setSqlSessionFactory(batchDBSqlSessionFactory);
//        myBatisBatchItemWriter.setStatementId("edu.project.persistence.batchDB.BatVisMstMapper.insertBatVisMst");
//
////        myBatisBatchItemWriter.setItemToParameterConverter(createItemToParameterMapConverter(dataShareBean.getData("GetMappingTableOutVO").get(mapIndex++).getQry2()));
//        return myBatisBatchItemWriter;
//    }
//
//    public static <BatVisMstVO> Converter<BatVisMstVO, Map<String, Object>> createItemToParameterMapConverter(String qry2) {
//        return new Converter<BatVisMstVO, Map<String, Object>>() {
//            @Override
//            public Map<String, Object> convert(BatVisMstVO items) {
//                Map<String, Object> parameter = new HashMap<>();
//                parameter.put("items", items);
//                parameter.put("qry2", qry2);
//                return parameter;
//            }
//        };
//    }
//}
