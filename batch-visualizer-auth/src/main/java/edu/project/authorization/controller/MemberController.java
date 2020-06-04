package edu.project.authorization.controller;

import edu.project.authorization.domain.*;
import edu.project.authorization.repository.MemberRepository;
import edu.project.authorization.service.MemberService;
import edu.project.authorization.service.UserAuthService;
import edu.project.authorization.util.JwtTokenProvider;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@CrossOrigin("*")
@RestController
//@RequestMapping("/auth")
@Slf4j
public class MemberController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final Counter myCounter;


    public MemberController(MeterRegistry meterRegistry) {
//        meterRegistry.gaugeCollectionSize("meterRegistry", "size", new ArrayList<>());
        myCounter = meterRegistry.counter("my.counter", "tag1", "tag2");
    }


    /* 로그인 API */
    @ApiOperation(
            value = "로그인",
            httpMethod = "POST",
            notes = "로그인 API"
    )
    @PostMapping(value = "/signin")
    public AuthenticationResVO signIn(@RequestBody AuthenticationReqVO authenticationReqVO, HttpSession httpSession) {
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();

        String userId = authenticationReqVO.getUserId();
        String userPasswd = authenticationReqVO.getUserPasswd();
        log.info("controller login>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>signin" + userId + userPasswd);

        /* token 생성 후 token을 파라미터로 인증 진행.  Spring Security에서 설정한 인증 적용됨 */
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userId, userPasswd);
        Authentication authentication = authenticationManager.authenticate(token);
        log.info("token>>>>>>>>>>>>>>>>: " + token);
        log.info("httpSession.getId()>>>>>>>>>>>>>>>>: " + httpSession.getId());

        /* 인증받은 결과를 SecurityContextHolder에서 getContext()를 통해 context로 받아온 후,
        이 Context 인증결과를 Set해줌. 이로써 서버의 SecurityContext에는 인증값이 설정됨. */
        SecurityContextHolder.getContext().setAuthentication(authentication);

        /* Session의 속성값에 SecurityContext값을 넣어줌 */
        httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        /* Jwt 생성 */
        String jwt = jwtTokenProvider.generateToken(authentication);
        CustomHeaderResVO header = new CustomHeaderResVO(true, "0000", "Success");

        myCounter.increment();
        return new AuthenticationResVO(header, authentication.getName(), authentication.getAuthorities(), jwt);
    }

    /* 회원가입 API */
    @ApiOperation(
            value = "회원가입",
            httpMethod = "POST",
            notes = "회원가입 API"
    )
    @PostMapping(value = "/signup")
    public MemberVO signUp(@RequestBody AuthenticationReqVO authenticationReqVO, HttpSession httpSession) {

        MemberRoleVO memberRoleVO = new MemberRoleVO();
        memberRoleVO.setRoleName("USER");

        MemberVO memberVO = new MemberVO();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberVO.setUserPasswd(passwordEncoder.encode(authenticationReqVO.getUserPasswd()));
        memberVO.setUserId(authenticationReqVO.getUserId());
        memberVO.setUserEmail(authenticationReqVO.getUserEmail());
        memberVO.setUserFirstName(authenticationReqVO.getUserFirstName());
        memberVO.setUserLastName(authenticationReqVO.getUserLastName());
        memberVO.setMemberRole(memberRoleVO);

        return memberRepository.save(memberVO);
    }

    /* 회원 탈퇴 API */
    @ApiOperation(
            value = "회원탈퇴",
            httpMethod = "DELETE",
            notes = "회원탈퇴 API"
    )
    @DeleteMapping(value="/member/{userId}")
    public Boolean deleteUser(@PathVariable("userId") String userId,
                              HttpSession httpSession) {
        return memberService.deleteMember(userId);
    }

    /* 회원정보 변경 API */
    @ApiOperation(
            value = "회원정보변경",
            httpMethod = "PUT",
            notes = "회원정보변경 API"
    )
    @PutMapping(value = "/member/{userId}")
    public MemberVO updateUserInfo(@RequestBody AuthenticationReqVO authenticationReqVO,
                                   @PathVariable("userId") String userId,
                                   HttpSession httpSession) {
        return memberService.updateMember(userId, authenticationReqVO);
    }


    /* user의 정보를 가져오는 API. 입력값은 현재 설정된 user의 principal 정보를 가져옴 */
    @ApiOperation(
            value = "회원정보조회",
            httpMethod = "GET",
            notes = "회원정보조회 API"
    )
    @GetMapping(value = "/member/{userId}")
    public GetUserInfoResVO getUserInfo(@AuthenticationPrincipal UserDetails userDetails,
                                        @PathVariable("userId") String userId,
                                        HttpSession httpSession) {
        GetUserInfoResVO response = new GetUserInfoResVO();
        MemberVO memberVO = memberRepository.findByUserId(userId);   // userId로 user정보 조회
        response.setRoleName(memberVO.getMemberRole().getRoleName());
        response.setUserId(memberVO.getUserId());
        response.setUserEmail(memberVO.getUserEmail());
        response.setUserEmail(memberVO.getUserEmail());
        response.setUserFirstName(memberVO.getUserFirstName());
        response.setUserLastName(memberVO.getUserLastName());

        return response;
    }


    /* user의 정보를 가져오는 API. 입력값은 현재 설정된 user의 principal 정보를 가져옴 */
    @ApiOperation(
            value = "회원정보조회(TOKEN)",
            httpMethod = "GET",
            notes = "TOKEN으로 회원이 유효한지 체크하는 API"
    )
    @GetMapping(value = "/member")
    public GetUserInfoResVO getUserInfo(@AuthenticationPrincipal UserDetails userDetails,
                                        HttpSession httpSession) {
        GetUserInfoResVO response = new GetUserInfoResVO();
        MemberVO memberVO = memberRepository.findByUserId(userDetails.getUsername());   // 현재 principal에 셋팅된 userId로 user정보 조회
        response.setRoleName(memberVO.getMemberRole().getRoleName());
        response.setUserId(memberVO.getUserId());
        response.setUserEmail(memberVO.getUserEmail());
        response.setUserFirstName(memberVO.getUserFirstName());
        response.setUserLastName(memberVO.getUserLastName());

        return response;
    }

    /* Admin이 전체회원정보 조회*/
    @ApiOperation(
            value = "전체회원정보조회",
            httpMethod = "GET",
            notes = "Admin 권한을 가진 USER가 전체회원정보를 조회하는 API"
    )
    @GetMapping(value = "/admin/member")
    public List<GetUserInfoResVO> getAllUserInfo() {
        return memberService.getAllUserInfo();
    }

}