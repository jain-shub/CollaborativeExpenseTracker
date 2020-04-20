/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.webdev.exception.GroupExpenseException;
import com.projects.webdev.exception.UserException;
import com.projects.webdev.model.GroupsExpenses;
import com.projects.webdev.service.GroupsExpensesService;
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
public class GroupsExpenseController {

    @Autowired
    private GroupsExpensesService grpExpenseService;

//    Get all the grpExpenses
    @GetMapping("/api/grpExpense")
    public ResponseEntity<List<GroupsExpenses>> list() throws GroupExpenseException {
        try {
            List<GroupsExpenses> grpExpenseList = grpExpenseService.list();
            if (grpExpenseList == null) {
                return null;
            }
            return new ResponseEntity(grpExpenseList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    //    Save the grpExpenses
    @PostMapping("/api/grpExpense")
    public ResponseEntity<?> save(@RequestBody GroupsExpenses grpExpense, HttpServletRequest request) throws GroupExpenseException, UserException {
//        String id = 

        GroupsExpenses grpExp = grpExpenseService.save(grpExpense, request);
        String grpExpenseJson = "";

        try {
            ObjectMapper mapper = new ObjectMapper();
            grpExpenseJson = mapper.writeValueAsString(grpExp);
        } catch (JsonProcessingException e) {
            throw new Error(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(grpExpenseJson, HttpStatus.OK);
    }

    //    Get all the grpExpenses
    @GetMapping("/api/grpExpense/{id}")
    public ResponseEntity<GroupsExpenses> get(@PathVariable("id") String id) throws GroupExpenseException {
        try {
            GroupsExpenses grpExpense = grpExpenseService.get(id);
            return new ResponseEntity(grpExpense, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/api/grpExpense/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody GroupsExpenses grpExpense) throws GroupExpenseException {
        try {
            grpExpenseService.update(id, grpExpense);
        } catch (Exception e) {
            return new ResponseEntity(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok("GroupExpense has been update with id: " + id);
    }

    @DeleteMapping("/api/grpExpense/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) throws GroupExpenseException, UserException {
        try {
            grpExpenseService.delete(id);
        } catch (Exception e) {
            return new ResponseEntity(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(id, HttpStatus.OK);
    }
}
