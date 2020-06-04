package edu.project.authorization.service;

import edu.project.authorization.domain.AuthenticationReqVO;
import edu.project.authorization.domain.GetUserInfoResVO;
import edu.project.authorization.domain.MemberRoleVO;
import edu.project.authorization.domain.MemberVO;
import edu.project.authorization.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public List<GetUserInfoResVO> getAllUserInfo() {
        Iterable<MemberVO> memberIterable =  memberRepository.findAll();
        List<GetUserInfoResVO> memberList = new ArrayList<GetUserInfoResVO>();
        if (memberIterable != null) {
            for (MemberVO member: memberIterable) {
                GetUserInfoResVO res = new GetUserInfoResVO();
                res.setUserId(member.getUserId());
                res.setRoleName(member.getMemberRole().getRoleName());
                res.setUserEmail(member.getUserEmail());
                res.setUserFirstName(member.getUserFirstName());
                res.setUserLastName(member.getUserLastName());
                memberList.add(res);
            }
        }
        return memberList;
    }


    @Transactional
    @Override
    public MemberVO updateMember(String userId, AuthenticationReqVO authenticationReqVO) {
        String userPasswd;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        MemberVO oldMemberVO = memberRepository.findByUserId(userId);
        log.info("oldMemberVO >>>>>>" + oldMemberVO);

        if ("Settings".equals(authenticationReqVO.getPage())) { // Settings 페이지에서 유입된 경우(자기 프로필을 변경할 경우)
            userPasswd = passwordEncoder.encode(authenticationReqVO.getUserPasswd());   // 유입된 비밀번호를 인코딩처리
        } else {                                                // Settings 페이지에서 유입되지 않은 경우(어드민이 다른사람의 프로필을 변경할 경우)
            userPasswd = oldMemberVO.getUserPasswd();           // 현재 비밀번호를 가져옴
        }
        MemberVO newMemerVO = new MemberVO(
                0,
                oldMemberVO.getUserId(),
                userPasswd,
                authenticationReqVO.getUserEmail(),
                authenticationReqVO.getUserFirstName(),
                authenticationReqVO.getUserLastName(),
                null,
                null,
                "update",
                new Date(),
                new MemberRoleVO(
                        oldMemberVO.getMemberRole().getRno(),
                        authenticationReqVO.getRoleName(),
                        oldMemberVO.getMemberRole().getRegId(),
                        oldMemberVO.getMemberRole().getRegDt(),
                        "update",
                        new Date()));

        BeanUtils.copyProperties(newMemerVO, oldMemberVO, "id", "regId", "regDt", "updId");
        memberRepository.save(oldMemberVO);
        return oldMemberVO;
    }

    @Transactional
    @Override
    public Boolean deleteMember(String userId) {
        MemberVO memberVO = memberRepository.findByUserId(userId);
        log.info("memberVO >>>>>>" + memberVO);
        try {
            memberRepository.deleteById(memberVO.getId());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
