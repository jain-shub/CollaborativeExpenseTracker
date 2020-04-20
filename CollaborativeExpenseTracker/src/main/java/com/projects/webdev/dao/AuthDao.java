/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.dao;

import com.projects.webdev.exception.UserException;
import com.projects.webdev.model.User;

/**
 *
 * @author shubh
 */
public interface AuthDao {

    public User authenticate(String userName, String password) throws UserException;

    // Getting user details from userId
    public User getUser(String userId) throws UserException;

    User getByEmail(String email) throws UserException;

}
