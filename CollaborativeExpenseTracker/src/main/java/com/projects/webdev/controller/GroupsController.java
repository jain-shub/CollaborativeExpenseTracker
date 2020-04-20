/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.webdev.dao.AuthDao;
import com.projects.webdev.exception.GroupException;
import com.projects.webdev.exception.UserException;
import com.projects.webdev.model.Groups;
import com.projects.webdev.model.User;
import com.projects.webdev.service.GroupsService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author shubh
 */
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RestController
public class GroupsController {

    @Autowired
    AuthDao authDao;

    @Autowired
    private GroupsService groupsService;

//    Get all the groups
    @GetMapping("/api/groups")
    public ResponseEntity<List<Groups>> list(HttpServletRequest request) throws GroupException {
        try {
            List<Groups> groupsList = groupsService.list(request);
            System.out.println("in controller list");
            System.out.println(groupsList);
            if (groupsList == null) {
                return null;
            }
            return new ResponseEntity(groupsList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    //    Save the groups
    @PostMapping("/api/groups")
    public ResponseEntity<?> save(@RequestBody Groups groups, HttpServletRequest request) throws GroupException, UserException {
//        String id = 
        User usr = authDao.getUser((String) request.getAttribute("userId"));
        groupsService.save(String.valueOf(usr.getUserId()), groups, request);
        String groupJson = "";

        try {
            ObjectMapper mapper = new ObjectMapper();
            groupJson = mapper.writeValueAsString(groups);
        } catch (JsonProcessingException e) {
            throw new Error(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(groupJson, HttpStatus.OK);
    }

    //    Get all the groups
    @GetMapping("/api/groups/{id}")
    public ResponseEntity<Groups> get(@PathVariable("id") String id, HttpServletRequest request) throws GroupException {
        try {
            System.out.println("1st point");
            Groups groups = groupsService.get(id, request);
            return new ResponseEntity(groups, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/api/groups/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody Groups groups, HttpServletRequest request) throws GroupException, UserException {
        try {
            System.out.println("point y1");
            groupsService.update(id, groups, request);
            return new ResponseEntity(groups, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/api/groups/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) throws GroupException {
        try {
            groupsService.delete(id);
            return new ResponseEntity(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
