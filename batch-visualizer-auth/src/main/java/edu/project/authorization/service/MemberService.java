package edu.project.authorization.service;

import edu.project.authorization.domain.AuthenticationReqVO;
import edu.project.authorization.domain.GetUserInfoResVO;
import edu.project.authorization.domain.MemberVO;

import java.util.List;

public interface MemberService {
    public List<GetUserInfoResVO> getAllUserInfo();
    public MemberVO updateMember(String userId, AuthenticationReqVO authenticationReqVO);
    public Boolean deleteMember(String userId);
}
