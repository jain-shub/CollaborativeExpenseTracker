/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.service;

import com.projects.webdev.exception.ExpenseException;
import com.projects.webdev.exception.UserException;
import com.projects.webdev.model.Expenses;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author shubh
 */
public interface ExpenseService {

    //    Save the Expenses
    Expenses save(String userId, Expenses expense, HttpServletRequest request) throws ExpenseException, UserException;

//    get a single expense
    Expenses get(String id, HttpServletRequest request) throws ExpenseException;

//    get all expenses
    List<Expenses> list(String userId, HttpServletRequest request) throws ExpenseException, UserException;

//    update the expense
//    Expenses
    Expenses update(String id, Expenses expense, HttpServletRequest request) throws ExpenseException, UserException;

//    delete a expense
    void delete(String id, HttpServletRequest request) throws ExpenseException, UserException;
    
//    public void updateUserWithExpense(Expenses expense);
}
