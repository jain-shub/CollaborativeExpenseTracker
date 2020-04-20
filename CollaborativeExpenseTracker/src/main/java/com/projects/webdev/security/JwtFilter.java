/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.webdev.dao.JwtTokenUtilImpl;
import com.projects.webdev.exception.Message;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.projects.webdev.exception.Errors;

/**
 *
 * @author shubh
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtTokenUtilImpl jwtTokenUtil = new JwtTokenUtilImpl();

    public JwtTokenUtilImpl getJwtTokenUtil() {
        return jwtTokenUtil;
    }

    public void setJwtTokenUtil(JwtTokenUtilImpl jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest hsr, HttpServletResponse hsr1, FilterChain fc) throws ServletException, IOException {
        final String header = hsr.getHeader("Authorization");
        String userId = null;
        String token = null;
        System.out.println(isTokenValid(header));
        if (isTokenValid(header)) {
            System.out.println("point 9");

            token = header.substring(7);
            System.out.println(token);
            try {
                userId = jwtTokenUtil.getUserIdFromToken(token);
                if (userId != null) {
                    System.out.println("point 12");
                    hsr.setAttribute("userId", userId);
                }
            } catch (Exception e) {
                System.out.println("point 17");
                e.printStackTrace();
                displayError(hsr1);
                throw new Error("Unauthorized!");
            }

        } else {
            displayError(hsr1);
        }

        fc.doFilter(hsr, hsr1);
    }

    boolean isTokenValid(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return true;
        }
        return false;
    }

    void displayError(HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Message> errors = new ArrayList<>();
        errors.add(new Message("Token is not valid"));
        String Json = mapper.writeValueAsString(new Errors(errors));
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(Json);
    }
}
