/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.dao;

import com.projects.webdev.exception.ExpenseException;
import com.projects.webdev.model.Expenses;
import java.util.List;

/**
 *
 * @author shubh
 */
public interface ExpenseDao {

//    Save the Expenses
    Expenses save(Expenses expense) throws ExpenseException;

//    get a single expense
    Expenses get(String id) throws ExpenseException;

//    get all expenses
    List<Expenses> list() throws ExpenseException;

//    update the expense
    void update(String id, Expenses expense) throws ExpenseException;

//    delete a expense
    void delete(String id) throws ExpenseException;

}
