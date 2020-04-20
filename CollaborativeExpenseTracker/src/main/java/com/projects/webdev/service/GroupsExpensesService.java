/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.service;

import com.projects.webdev.exception.GroupExpenseException;
import com.projects.webdev.exception.UserException;
import com.projects.webdev.model.GroupsExpenses;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author shubh
 */
public interface GroupsExpensesService {

    //    Save the GroupsExpenses
    GroupsExpenses save(GroupsExpenses expense, HttpServletRequest request) throws GroupExpenseException,UserException;

//    get a single expense
    GroupsExpenses get(String id) throws GroupExpenseException;

//    get all expenses
    List<GroupsExpenses> list() throws GroupExpenseException;

//    update the expense
    void update(String id, GroupsExpenses expense) throws GroupExpenseException;

//    delete a expense
    void delete(String id) throws GroupExpenseException;
}
