/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.dao;

import com.projects.webdev.exception.GroupExpenseException;
import com.projects.webdev.model.GroupsExpenses;
import java.util.List;

/**
 *
 * @author shubh
 */
public interface GroupsExpenseDao{
    //    Save the GroupsExpenses
    GroupsExpenses save(GroupsExpenses expense) throws GroupExpenseException;

//    get a single expense
    GroupsExpenses get(String id) throws GroupExpenseException;

//    get all expenses
    List<GroupsExpenses> list() throws GroupExpenseException;

//    update the expense
    void update(String id, GroupsExpenses expense) throws GroupExpenseException;

//    delete a expense
    void delete(String id) throws GroupExpenseException;
}
