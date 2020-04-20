/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.dao;

import com.projects.webdev.exception.GroupException;
import com.projects.webdev.model.Groups;
import java.util.List;


/**
 *
 * @author shubh
 */
public interface GroupsDao {
    //    Save the Groups
    Groups save(Groups expense) throws GroupException;

//    get a single expense
    Groups get(String id) throws GroupException;

//    get all expenses
    List<Groups> list() throws GroupException;

//    update the expense
    void update(String id, Groups expense) throws GroupException;

//    delete a expense
    void delete(String id) throws GroupException;
}
