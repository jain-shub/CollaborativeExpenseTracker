/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.dao;

import static com.projects.webdev.dao.Dao.close;
import static com.projects.webdev.dao.Dao.getSession;
import com.projects.webdev.exception.ExpenseException;
import com.projects.webdev.model.Expenses;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author shubh
 */
@Repository
public class ExpenseDaoImpl extends Dao implements ExpenseDao {

    @Autowired
    private AuthDao authDao;
    
    @Override
    public Expenses save(Expenses expense) throws ExpenseException {
        try {
            begin();
            getSession().save(expense);
            commit();
            return expense;
        } catch (HibernateException ex) {
            rollback();
            throw new ExpenseException("Exception while savng the expense: " + ex.getMessage());
        } catch (Exception ex) {
            rollback();
            throw new Error("Failed attempt to save expense!" + ex.getMessage());
        } finally {
            close();
        }

    }

    @Override
    public Expenses get(String id) throws ExpenseException {
        try {
            begin();
            System.out.println("Point 5a");
            System.out.println(id);
            Expenses exp = getSession().get(Expenses.class, Long.parseLong(id));
            
            commit();
            return exp;
        } catch (HibernateException ex) {
            rollback();
            throw new ExpenseException("Exception while getting the expenses: " + ex.getMessage());
        } catch (Exception ex) {
            rollback();
            throw new Error("Failed attempt to retrieve expenses!" + ex.getMessage());
        } finally {
            close();
        }

    }

    @Override
    public List<Expenses> list() throws ExpenseException {
        try {
            begin();
            List<Expenses> expenseList = getSession().createQuery("from UserExpense").list();
            commit();
            return expenseList;
        } catch (HibernateException ex) {
            rollback();
            throw new ExpenseException("Exception while getting the expenses: " + ex.getMessage());
        } catch (Exception ex) {
            rollback();
            throw new Error("Failed attempt to retrieve expenses!" + ex.getMessage());
        } finally {
            close();
        }

    }

    @Override
    public void update(String id, Expenses expense) throws ExpenseException {
        try {
            begin();
            Session session = getSession();
            Expenses oldExpenses = session.byId(Expenses.class).load(Long.parseLong(id));
            oldExpenses.setName(expense.getName());
//        oldExpenses.setCategory(expense.getCategory());
            oldExpenses.setType(expense.getType());
            oldExpenses.setFromUser(expense.getFromUser());
            oldExpenses.setToUser(expense.getToUser());
            oldExpenses.setAmount(expense.getAmount());
            oldExpenses.setFromUserSplitPercentageValue(expense.getFromUserSplitPercentageValue());
            oldExpenses.setToUserSplitPercentageValue(expense.getToUserSplitPercentageValue());
            oldExpenses.setFromUserName(expense.getFromUserName());
            oldExpenses.setToUserName(expense.getToUserName());
            commit();
//            close();
            begin();
            session.flush();
            commit();
        } catch (HibernateException ex) {
            rollback();
            throw new ExpenseException("Exception while updating the expense: " + ex.getMessage());
        } catch (Exception ex) {
            rollback();
            throw new Error("Failed attempt to update expense!" + ex.getMessage());
        } finally {
            close();
        }

    }

    @Override
    public void delete(String id) throws ExpenseException {
        try {
            begin();
            Session session = getSession();
            Expenses expense = session.byId(Expenses.class).load(Long.parseLong(id));
            session.delete(expense);
            commit();
        } catch (HibernateException ex) {
            rollback();
            throw new ExpenseException("Exception while getting the user: " + ex.getMessage());
        } catch (Exception ex) {
            rollback();
            throw new Error("Failed attempt to retrieve user!" + ex.getMessage());
        } finally {
            close();
        }

    }

}
