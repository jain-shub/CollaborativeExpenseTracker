/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.service;

import com.projects.webdev.exception.GroupException;
import com.projects.webdev.exception.UserException;
import com.projects.webdev.model.Groups;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author shubh
 */
public interface GroupsService{
    //    Save the Groups

    Groups save(String userId, Groups expense, HttpServletRequest request) throws GroupException, UserException;

//    get a single expense
    Groups get(String id, HttpServletRequest request) throws GroupException;

//    get all expenses
    List<Groups> list(HttpServletRequest request) throws GroupException;

//    update the expense
    void update(String id, Groups expense, HttpServletRequest request) throws GroupException, UserException;

//    delete a expense
    void delete(String id) throws GroupException;
}
