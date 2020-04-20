/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.dao;

import static com.projects.webdev.dao.Dao.getSession;
import com.projects.webdev.exception.UserException;
import com.projects.webdev.model.User;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author shubh
 */
@Repository
public class UserDaoImpl extends Dao implements UserDao {

//    @Autowired
//    private SessionFactory sessionFactory;
    @Override
    public User save(User user) throws UserException {
        try {
            begin();
            Hibernate.initialize(user.getDueExpenses());
            Hibernate.initialize(user.getOwedExpenses());
            Hibernate.initialize(user.getGrouplist());
            getSession().save(user);
            commit();
            return user;
        } catch (HibernateException ex) {
            rollback();
            throw new UserException("Exception while registering the customer: " + ex.getMessage());
        } catch (Exception ex) {
            rollback();
            throw new Error("Failed attempt to register!" + ex.getMessage());
        } finally {
            close();
        }

    }

    @Override
    public User get(String id) throws UserException {
        try {
            begin();
//            User user = getSession().get(User.class, id);
            Query q = getSession().createQuery("FROM User WHERE userId=:userId");
            q.setLong("userId", Long.parseLong(id));
            User user = (User) q.uniqueResult();
            user.getDueExpenses().size();
            user.getOwedExpenses().size();
            user.getGrouplist().size();
            Hibernate.initialize(user.getDueExpenses());
            Hibernate.initialize(user.getOwedExpenses());
            Hibernate.initialize(user.getGrouplist());
            commit();
            return user;
        } catch (HibernateException ex) {
            rollback();
            throw new UserException("Exception while getting the user: " + ex.getMessage());
        } catch (Exception ex) {
            rollback();
            throw new Error("Failed attempt to retrieve user!" + ex.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public List<User> list() throws UserException {
        try {
            begin();
            List<User> userList = getSession().createQuery("from User").list();
            for (User user : userList) {
                Hibernate.initialize(user.getDueExpenses());
                Hibernate.initialize(user.getOwedExpenses());
                Hibernate.initialize(user.getGrouplist());
            }
            commit();
            return userList;

        } catch (HibernateException ex) {
            rollback();
            throw new UserException("Exception while getting the user: " + ex.getMessage());
        } catch (Exception ex) {
            rollback();
            throw new Error("Failed attempt to retrieve user!" + ex.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public void update(String id, User user) throws UserException {
        try {
            begin();
            System.out.println("point a");
            Session session = getSession();
            System.out.println("point b");
//            User oldUser = session.byId(User.class).load(id);
            User oldUser = session.byId(User.class).load(Long.parseLong(id));
            System.out.println("point c");
            System.out.println(oldUser.toString());
            commit();
            oldUser.setFirstName(user.getFirstName());
            oldUser.setLastName(user.getLastName());
            oldUser.setPassword(user.getPassword());
            oldUser.setEmail(user.getEmail());
            oldUser.setAmountDue(user.getAmountDue());
            oldUser.setAmountOwed(user.getAmountOwed());
            begin();
//            Hibernate.initialize(user.getDueExpenses());
//            Hibernate.initialize(user.getOwedExpenses());
//            Hibernate.initialize(user.getGrouplist());
//            if (user.getDueExpenses() != null) {
//                oldUser.setDueExpenses(user.getDueExpenses());
//            }
//
//            if (user.getOwedExpenses() != null) {
//                oldUser.setOwedExpenses((Set<Expenses>) user.getOwedExpenses());
//            }
            System.out.println("point1a");
            String hql = "UPDATE User set firstName=:firstName, lastName=:lastName, email=:email, amountDue=:amountDue, amountOwed=:amountOwed WHERE userId=:userId";
            System.out.println("point1b");
//            , dueExpenses=:dueExpense, owedExpenses=:owedExpenses 
            Query query = getSession().createQuery(hql);
            query.setParameter("userId", oldUser.getUserId());
            query.setParameter("firstName", oldUser.getFirstName());
            query.setParameter("lastName", oldUser.getLastName());
            query.setParameter("email", oldUser.getEmail());
            System.out.println("point1c");
//            query.setParameter("dueExpenses", oldUser.getDueExpenses());
//            query.setParameter("owedExpenses", oldUser.getOwedExpenses());
            query.setParameter("amountDue", oldUser.getAmountDue());
            query.setParameter("amountOwed", oldUser.getAmountOwed());
            System.out.println("point1d");
            query.executeUpdate();
//            commit();
//            begin();
            System.out.println("point1e");
            session.flush();
            commit();
        } catch (HibernateException ex) {
            rollback();
            throw new UserException("Exception while getting the user: " + ex.getMessage());
        } catch (Exception ex) {
            rollback();
            throw new Error("Failed attempt to retrieve user!" + ex.getMessage());
        }
//        finally {
//            close();
//        }

    }

    @Override
    public void delete(String id) throws UserException {
        try {
            begin();
//            Session session = sessionFactory.getCurrentSession();
            User user = getSession().byId(User.class).load(Long.parseLong(id));
            Hibernate.initialize(user.getDueExpenses());
            Hibernate.initialize(user.getOwedExpenses());
            Hibernate.initialize(user.getGrouplist());
            getSession().delete(user);
            commit();
        } catch (HibernateException ex) {
            rollback();
            throw new UserException("Exception while getting the user: " + ex.getMessage());
        } catch (Exception ex) {
            rollback();
            throw new Error("Failed attempt to retrieve user!" + ex.getMessage());
        } finally {
            close();
        }

    }

}
