/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.dao;

import com.projects.webdev.exception.UserException;
import com.projects.webdev.model.User;
import java.util.List;

/**
 *
 * @author shubh
 */
public interface UserDao {

//    Save the User
    User save(User user) throws UserException;

//    get a single user
    User get(String id)  throws UserException;

//    get all users
    List<User> list() throws UserException;

//    update the user
    void update(String id, User user) throws UserException;

//    delete a user
    void delete(String id) throws UserException;
    

}
