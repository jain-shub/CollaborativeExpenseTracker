/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.service;

import com.projects.webdev.dao.AuthDao;
import com.projects.webdev.dao.UserDao;
import com.projects.webdev.exception.UserException;
import com.projects.webdev.helperclasses.Validations;
import com.projects.webdev.model.User;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author shubh
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthDao authDao;

    @Override
    @Transactional
    public User save(User user) throws UserException {
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        if (authDao.getByEmail(user.getEmail()) != null) {
            throw new Error("Email already exists");
        }
        newUser.setEmail(user.getEmail());

//        Write validations for password strength
        Validations validate = new Validations();
        if (!validate.isLongEnough(user.getPassword()) || !validate.isGoodPassword(user.getPassword()) || !validate.isSafe(user.getPassword())) {
            throw new Error("Password is not strong enough");
        }

        newUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userDao.save(newUser);
    }

    @Override
    @Transactional
    public User get(String id) throws UserException {
        return userDao.get(id);
    }

    @Override
    @Transactional
    public List<User> list() throws UserException {
        return userDao.list();
    }

    @Override
    @Transactional
    public void update(String id, User user, HttpServletRequest request) throws UserException {
        System.out.println(id);
        System.out.println("point g");
        User usr = userDao.get(id);
        System.out.println("point h");
        System.out.println(usr);
        String currentUserId = (String) request.getAttribute("userId");
        System.out.println("point i");
        System.out.println(currentUserId);
        System.out.println(id.equals(currentUserId));
        System.out.println(usr == null);

        if (usr == null || !id.equals(currentUserId)) {
            throw new Error("The user details supplied are invalid");
        }
        usr.setFirstName(user.getFirstName());
        usr.setLastName(user.getLastName());

        if (usr.getEmail() != user.getEmail()) {
            usr.setEmail(user.getEmail());
        }

        Validations validate = new Validations();
        if (!validate.isLongEnough(user.getPassword()) || !validate.isGoodPassword(user.getPassword()) || !validate.isSafe(user.getPassword()) || user.getPassword() == null) {
            throw new Error("Password is not strong enough");
        }

        usr.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userDao.update(id, usr);
    }

    @Override
    @Transactional
    public void delete(String id, HttpServletRequest request) throws UserException {
        String currentUserId = (String) request.getAttribute("userId");
        if (id != currentUserId) {
            throw new Error("The user details supplied are invalid");
        }
        userDao.delete(id);
    }

}
