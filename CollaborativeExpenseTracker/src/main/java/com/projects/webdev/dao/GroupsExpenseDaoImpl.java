/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.dao;

import com.projects.webdev.exception.GroupExpenseException;
import com.projects.webdev.model.GroupsExpenses;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author shubh
 */
@Repository
public class GroupsExpenseDaoImpl extends Dao implements GroupsExpenseDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public GroupsExpenses save(GroupsExpenses grpExpense) throws GroupExpenseException {
        try {
            begin();
            sessionFactory.getCurrentSession().save(grpExpense);
            commit();
            return grpExpense;
        } catch (HibernateException ex) {
            rollback();
            throw new GroupExpenseException("Exception while savng the expense: " + ex.getMessage());
        } catch (Exception ex) {
            rollback();
            throw new Error("Failed attempt to save expense!" + ex.getMessage());
        } finally {
            close();
        }

    }

    @Override
    public GroupsExpenses get(String id) throws GroupExpenseException {
        try {
            begin();
            GroupsExpenses expense = sessionFactory.getCurrentSession().get(GroupsExpenses.class, id);
            commit();
            return expense;
        } catch (HibernateException ex) {
            rollback();
            throw new GroupExpenseException("Exception while getting the expense: " + ex.getMessage());
        } catch (Exception ex) {
            rollback();
            throw new Error("Failed attempt to get expense!" + ex.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public List<GroupsExpenses> list() throws GroupExpenseException {
        try {
            begin();
            List<GroupsExpenses> grpExpenseList = sessionFactory.getCurrentSession().createQuery("from GroupsExpenses").list();
            commit();
            return grpExpenseList;
        } catch (HibernateException ex) {
            rollback();
            throw new GroupExpenseException("Exception while getting all the expense: " + ex.getMessage());
        } catch (Exception ex) {
            rollback();
            throw new Error("Failed attempt to get all expense!" + ex.getMessage());
        } finally {
            close();
        }

    }

    @Override
    public void update(String id, GroupsExpenses grpExpense) throws GroupExpenseException {
        try {
            begin();
            Session session = sessionFactory.getCurrentSession();
            GroupsExpenses oldGroupsExpenses = session.byId(GroupsExpenses.class).load(Long.parseLong(id));
            oldGroupsExpenses.setName(grpExpense.getName());
            oldGroupsExpenses.setCreatedBy(grpExpense.getCreatedBy());
            oldGroupsExpenses.setAmount(grpExpense.getAmount());
            oldGroupsExpenses.setDividePercentage(grpExpense.getDividePercentage());
            oldGroupsExpenses.setPaidBy(grpExpense.getPaidBy());
            commit();
//close();
            begin();
            session.flush();
            commit();
        } catch (HibernateException ex) {
            rollback();
            throw new GroupExpenseException("Exception while updating the expense: " + ex.getMessage());
        } catch (Exception ex) {
            rollback();
            throw new Error("Failed attempt to update expense!" + ex.getMessage());
        } finally {
            close();
        }

    }

    @Override
    public void delete(String id) throws GroupExpenseException {
        try {
            begin();
            Session session = sessionFactory.getCurrentSession();
            GroupsExpenses grpExpense = session.byId(GroupsExpenses.class).load(Long.parseLong(id));
            session.delete(grpExpense);
            commit();
        } catch (HibernateException ex) {
            rollback();
            throw new GroupExpenseException("Exception while deleting the expense: " + ex.getMessage());
        } catch (Exception ex) {
            rollback();
            throw new Error("Failed attempt to delete expense!" + ex.getMessage());
        } finally {
            close();
        }

    }
}
