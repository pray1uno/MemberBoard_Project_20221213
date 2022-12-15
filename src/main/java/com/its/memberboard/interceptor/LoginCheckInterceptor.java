package com.its.memberboard.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {
        String requestURL = request.getRequestURI();
        HttpSession session = request.getSession();

        if (session.getAttribute("login") == null) {
            response.sendRedirect("/member/login?redirectURL=" + requestURL);
            return false;
        }
        return true;
    }
}
