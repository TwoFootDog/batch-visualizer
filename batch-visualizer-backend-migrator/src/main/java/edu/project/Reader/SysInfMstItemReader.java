//package edu.project.Reader;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.batch.MyBatisPagingItemReader;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.NonTransientResourceException;
//import org.springframework.batch.item.ParseException;
//import org.springframework.batch.item.UnexpectedInputException;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@RequiredArgsConstructor
//@Component
//@StepScope
//public class SysInfMstItemReader implements ItemReader<SysInfMstVO> {
//
//    private final SqlSessionFactory sqlSessionFactory;
//
//    private final MyBatisPagingItemReader<SysInfMstVO> reader;
//
//    @Value("${chunkSize:1000}")
//    private int chunkSize;
//
//
//    @Override
//    public SysInfMstVO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
//
//        SysInfMstVO sysInfMstVO = reader.read();
//
//        log.info(">>>>>>>reader result : ", sysInfMstVO);
//
//
//        return sysInfMstVO;
//    }
//}
