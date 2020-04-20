/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.webdev.dao.AuthDao;
import com.projects.webdev.exception.UserException;
import com.projects.webdev.model.User;
import com.projects.webdev.exception.Errors;
import com.projects.webdev.security.JwtResponse;
import com.projects.webdev.exception.Message;
import com.projects.webdev.dao.JwtTokenUtil;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author shubh
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

//    @SuppressWarnings("unchecked")
//	@GetMapping(value = "user/login")
//	public Boolean login(@PathVariable String email, @PathVariable String password,
//			final HttpServletRequest request) throws ServletException {
//            
//            
//		final Claims claims = (Claims) request.getAttribute("claims");
//
//		return ((List<String>) claims.get("users")).contains(email);
//	}
//    @Qualifier("authDao")
    @Autowired
    AuthDao authDao;

//    @Qualifier("jwtTokenUtil")
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    // User Login and return token
    // access: private 
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Object> createAuthenticationToken(@RequestBody User u) throws Exception {
//    public ResponseEntity<Object> createAuthenticationToken(@RequestBody String email, @RequestBody String password) throws Exception {
        try {
            System.out.println("Login method");
            System.out.println(u.getEmail() + " " + u.getPassword());
            User user = authDao.authenticate(u.getEmail(), u.getPassword());
            final String token = jwtTokenUtil.generateToken(user);
            String userJson = "";
            ObjectMapper mapper = new ObjectMapper();
            JSONObject myObject = new JSONObject();
            myObject.put("user", user);
            myObject.put("jwtToken", token);
            userJson = mapper.writeValueAsString(myObject);
//            userJson = mapper.writeValueAsString(user);
            return new ResponseEntity<>(userJson, HttpStatus.OK);
//            return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
        } catch (UserException e) {
            List<Message> errors = new ArrayList<>();
            errors.add(new Message(e.getMessage()));
            return new ResponseEntity<>(new Errors(errors), HttpStatus.BAD_REQUEST);
        }
    }

    // Getting user details from token
    // access: private
    @RequestMapping(value = "/getUserDetails", method = RequestMethod.GET)
    public ResponseEntity<Object> getUserDetails(HttpServletRequest request) throws Exception {
        try {
            User user = authDao.getUser((String) request.getAttribute("userId"));
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            List<Message> errors = new ArrayList<>();
            errors.add(new Message(e.getMessage()));
            return new ResponseEntity<>(new Errors(errors), HttpStatus.BAD_REQUEST);
        }
    }

}
