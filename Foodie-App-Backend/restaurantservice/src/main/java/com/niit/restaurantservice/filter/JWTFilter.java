package com.niit.restaurantservice.filter;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class JWTFilter  extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse=(HttpServletResponse) servletResponse;

        String authHeader=httpServletRequest.getHeader("Authorization");
        if(authHeader==null){
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ServletOutputStream outputStream=httpServletResponse.getOutputStream();
            outputStream.println("Token Missing");
        }
        else{
            String jwtToken=authHeader.substring(7);
            String userdetail= Jwts.parser().setSigningKey("Password").parseClaimsJws(jwtToken).getBody().getSubject();
            httpServletRequest.setAttribute("emailId",userdetail);
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }
}

