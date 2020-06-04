package edu.project.authorization.repository;

import edu.project.authorization.domain.MemberRoleVO;
import edu.project.authorization.domain.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Equals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;


    @Before
    public void beforeTest() {
        MemberVO memberVO = new MemberVO();
        MemberRoleVO memberRoleVO = new MemberRoleVO();
        memberVO.setUserId("TEST");
        memberVO.setUserPasswd("TEST");
        memberVO.setUserEmail("TEST@gmail.com");
        memberVO.setUserFirstName("TEST");
        memberVO.setUserLastName("TEST");
        memberRoleVO.setRoleName("USER");
        memberVO.setMemberRole(memberRoleVO);
        memberRepository.save(memberVO);
    }

    @Test
    public void findByUserId() {
        String userId = "TEST";
        MemberVO memberVO = memberRepository.findByUserId(userId);
//        log.info("memberVO >>>>" + memberVO);
        assertNotNull(memberVO);
    }

    @After
    public void afterTest() {
        String userId = "TEST";
        MemberVO memberVO = memberRepository.findByUserId(userId);
        memberRepository.delete(memberVO);
    }
}
