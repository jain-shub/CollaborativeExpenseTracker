/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.dao;

import com.projects.webdev.exception.UserException;
import com.projects.webdev.model.User;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author shubh
 */
@Repository
public class AuthDaoImpl extends Dao implements AuthDao {

//    @Autowired
//    private SessionFactory sessionFactory;
    // User authentication
    @Override
    public User authenticate(String userName, String password) throws UserException {
        try {
            System.out.println("point 1");
            begin();
            System.out.println("point 2");
            Query q = getSession().createQuery("FROM User WHERE email=:userName");
            q.setString("userName", userName);
            List<User> list = q.list();
            if (list.isEmpty()) {
                System.out.println("point 5");
                throw new HibernateException("Username or Password is invalid");
            }
            User user = list.get(0);
            System.out.println("point 6");
            System.out.println(user.toString());
            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            System.out.println("point 7");
            System.out.println(password);
            System.out.println(user.getPassword());
            System.out.println(bcrypt.matches(password, user.getPassword()));
            if (!bcrypt.matches(password, user.getPassword())) {
                System.out.println("point 8");
                throw new HibernateException("Username or Password is invalid");
            }
            commit();
            close();
            return user;
        } catch (HibernateException e) {
            rollback();
            throw new UserException(e.getMessage() + "in auth dao exception");
        }
    }

    // Getting user details from userId
    @Override
    public User getUser(String userId) throws UserException {
        try {
            begin();
//            User user = getSession().get(User.class, Integer.parseInt(userId));
//            User user = getSession().get(User.class, userId);
            Query q = getSession().createQuery("FROM User WHERE userId=:userId");
            q.setString("userId", userId);
            User user = (User) q.uniqueResult();
//            List<User> list = q.list();
            commit();
            close();
            return user;
        } catch (HibernateException e) {
            rollback();
            throw new UserException("User does not exist");
        }
    }

    @Override
    public User getByEmail(String email) throws UserException {
        try {
            begin();
            org.hibernate.query.Query q = getSession().createQuery("from User where email = :email");
            q.setString("email", email);
            User results = (User) q.uniqueResult();
            commit();
            close();
            return results;
        } catch (HibernateException ex) {
            rollback();
            throw new UserException("Exception while getting the user: " + ex.getMessage());
        } catch (Exception ex) {
            rollback();
            throw new Error("Failed attempt to retrieve user!" + ex.getMessage());
        }
    }
}
