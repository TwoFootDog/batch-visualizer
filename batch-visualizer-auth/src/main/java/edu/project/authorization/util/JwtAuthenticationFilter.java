package edu.project.authorization.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {    // Jwt가 유효한 토큰인지 인증하기 위한 Fillter

//    private static final Logger logger = LogManager.getLogger(JwtAuthenticationFilter.class.getName());
    Logger logger = LoggerFactory.getLogger(getClass());
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token  = jwtTokenProvider.resolveToken((HttpServletRequest)request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //log.info("token dofilter >>>>>>>>");
            logger.info("token dofilter >>>>>>>");
            logger.warn("token dofilter >>>>>>>");
        } else {
            SecurityContextHolder.getContext().setAuthentication(null);
            //log.info("token is invalid>>>>>>>");
            logger.info("token is invalid>>>>>>>");
            logger.warn("token is invalid>>>>>>>");
            logger.debug("token is invalid>>>>>>>");
            logger.trace("token is invalid>>>>>>>");
            logger.error("token is invalid>>>>>>>");
        }
        logger.info("doFilter>>>>>>>>>>>>>>>>>>>>>>" + token);
        logger.info("ServletRequest>>>>>>>>>>>>>>>>>>>>>>" + ((HttpServletRequest) request).getHeader("X-AUTH-TOKEN"));
        chain.doFilter(request, response);  // Filter를 FilterChain에 등록
    }
}
