/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.webdev.exception.UserException;
import com.projects.webdev.model.User;
import com.projects.webdev.service.UserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author shubh
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

//    @Autowired
//    @Qualifier("authDao")
//    AuthDao authDao;
    private String token;

    public UserController() {
    }

//    Get all the users
    @GetMapping("/api/user")
    public ResponseEntity<List<User>> list() throws UserException {
        List<User> userList = userService.list();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    //    Save the users
    @PostMapping(value = "/api/auth", produces = "application/json")
    public ResponseEntity<?> save(@RequestBody User user) throws UserException {
        User usr = userService.save(user);
        String userJson = "";

        try {
            ObjectMapper mapper = new ObjectMapper();
            userJson = mapper.writeValueAsString(usr);
        } catch (JsonProcessingException e) {
            throw new Error(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userJson, HttpStatus.OK);
    }

    //    Get all the users
    @GetMapping("/api/user/get/{id}")
    public ResponseEntity<User> get(@PathVariable("id") String id) throws UserException {
        try {
            User user = userService.get(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/api/user/put/{id}", produces = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody User user, HttpServletRequest request) throws UserException {
        userService.update(id, user, request);
        String userJson = "";

        try {
            ObjectMapper mapper = new ObjectMapper();
            userJson = mapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new Error(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userJson, HttpStatus.OK);
    }

    @PostMapping("/api/user/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id, HttpServletRequest request) throws UserException {
        try {
            userService.delete(id, request);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
