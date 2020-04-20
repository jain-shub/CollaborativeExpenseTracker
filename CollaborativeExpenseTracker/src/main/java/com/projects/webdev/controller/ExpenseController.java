/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.webdev.dao.AuthDao;
import com.projects.webdev.exception.Errors;
import com.projects.webdev.exception.ExpenseException;
import com.projects.webdev.exception.Message;
import com.projects.webdev.exception.UserException;
import com.projects.webdev.model.Expenses;
import com.projects.webdev.model.User;
import com.projects.webdev.service.ExpenseService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author shubh
 */
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RestController
public class ExpenseController {

    @Autowired
    AuthDao authDao;

    @Autowired
    private ExpenseService expenseService;

//    Get all the expenses
    @GetMapping("/api/expense")
    public ResponseEntity<List<Expenses>> list(HttpServletRequest request) throws ExpenseException, UserException {
        try {
            System.out.println("to get user list");
            System.out.println(request.toString());
            System.out.println(request.getAttribute("userId"));
            User usr = authDao.getUser((String) request.getAttribute("userId"));
            System.out.println(usr.toString());
            List<Expenses> expenseList = expenseService.list(String.valueOf(usr.getUserId()), request);
            System.out.println(expenseList.toArray().toString());
            if (expenseList == null) {
                throw new ExpenseException("Getting list of expenses failed");
            }
            return new ResponseEntity<>(expenseList, HttpStatus.OK);
        } catch (Exception e) {
            List<Message> errors = new ArrayList<>();
            errors.add(new Message(e.getMessage()));
            return new ResponseEntity(new Errors(errors), HttpStatus.BAD_REQUEST);
        }
    }

    //    Save the expenses
//    , produces = "application/json"
    @PostMapping(value = "/api/expense")
    public ResponseEntity<?> save(@RequestBody Expenses expense, HttpServletRequest request) throws ExpenseException, UserException {
        System.out.println("in save method");
        System.out.println(expense);
//        expenseService.updateUserWithExpense(expense);
//        String id = expenseService.save(expense.getFromUser().getUserId(),expense);
        User usr = authDao.getUser((String) request.getAttribute("userId"));
        Expenses newExp = expenseService.save(String.valueOf(usr.getUserId()), expense, request);
        String expenseJson = "";

        try {
            ObjectMapper mapper = new ObjectMapper();
            expenseJson = mapper.writeValueAsString(newExp);
        } catch (JsonProcessingException e) {
            return new ResponseEntity(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(expenseJson, HttpStatus.OK);
    }

    //    Get all the expenses
    @GetMapping("/api/expense/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id, HttpServletRequest request) throws ExpenseException {
        Expenses expense = expenseService.get(id, request);
        String expenseJson = "";

        try {
            ObjectMapper mapper = new ObjectMapper();
            expenseJson = mapper.writeValueAsString(expense);
        } catch (Exception e) {
            return new ResponseEntity(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(expenseJson, HttpStatus.OK);
    }

    @PostMapping("/api/expense/put/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody Expenses expense, HttpServletRequest request) throws ExpenseException, UserException {
        String expenseJson = "";
        System.out.println("Point 09");
        System.out.println(expense.toString());
        System.out.println(request.toString());
        System.out.println(id);
        expenseService.update(id, expense, request);
        try {
            ObjectMapper mapper = new ObjectMapper();
            expenseJson = mapper.writeValueAsString(expense);
        } catch (JsonProcessingException e) {
            throw new Error(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(expenseJson, HttpStatus.OK);
    }

    @PostMapping("/api/expense/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id, HttpServletRequest request) throws ExpenseException, UserException {
        try {
            expenseService.delete(id, request);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
