/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.service;

import com.projects.webdev.dao.AuthDao;
import com.projects.webdev.dao.GroupsExpenseDao;
import com.projects.webdev.exception.GroupExpenseException;
import com.projects.webdev.exception.UserException;
import com.projects.webdev.model.Groups;
import com.projects.webdev.model.GroupsExpenses;
import com.projects.webdev.model.User;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author shubh
 */
@Service
public class GroupsExpensesServiceImpl implements GroupsExpensesService {
    @Autowired
    private GroupsExpenseDao expenseDao;
    
    @Autowired
    AuthDao authDao;
    
    @Override
    @Transactional
    public GroupsExpenses save(GroupsExpenses grpExpense, HttpServletRequest request) throws GroupExpenseException,UserException {
        GroupsExpenses grpExp = new GroupsExpenses();
        System.out.println(authDao.getUser((String) request.getAttribute("userId")));
        grpExp.setCreatedBy(authDao.getUser((String) request.getAttribute("userId")));
        grpExp.setSourceGroup((Groups) request.getAttribute("currentGroup"));
        grpExp.setName(grpExpense.getName());
        grpExp.setAmount(grpExpense.getAmount());
        grpExp.setPaidBy(grpExpense.getPaidBy());
        grpExp.setDividePercentage(grpExpense.getDividePercentage());
        return expenseDao.save(grpExp);
    }

    @Override
    @Transactional
    public GroupsExpenses get(String id) throws GroupExpenseException {
        return expenseDao.get(id);
    }

    @Override
    @Transactional
    public List<GroupsExpenses> list() throws GroupExpenseException {
        return expenseDao.list();
    }

    @Override
    @Transactional
    public void update(String id, GroupsExpenses expense) throws GroupExpenseException {
        expenseDao.update(id, expense);
    }

    @Override
    @Transactional
    public void delete(String id) throws GroupExpenseException {
        expenseDao.delete(id);
    }
}
