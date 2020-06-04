//package edu.project.Writer;
//
//import lombok.extern.slf4j.Slf4j;
//import org.mybatis.spring.batch.MyBatisBatchItemWriter;
//import org.springframework.batch.core.StepExecution;
//import org.springframework.batch.core.annotation.BeforeStep;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.ExecutionContext;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Slf4j
//@Component
//@StepScope
//public class SysInfMstItemWriter implements ItemWriter<Object> {
//
//    private StepExecution stepExecution;
//    private MyBatisBatchItemWriter
//
//    @Override
//    public void write(List<?> items) throws Exception {
////        ExecutionContext stepContext = this.stepExecution.getExecutionContext();
////        stepContext.put("item", items);
//        log.info(">>>>>>>>>>>>>>writer : ", items);
//    }
//
////    @BeforeStep
////    public void saveStepException(final StepExecution stepExecution) {
////        this.stepExecution = stepExecution;
////    }
//}
