/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.service;

import com.projects.webdev.dao.AuthDao;
import com.projects.webdev.dao.ExpenseDao;
import com.projects.webdev.dao.UserDao;
import com.projects.webdev.exception.ExpenseException;
import com.projects.webdev.exception.UserException;
import com.projects.webdev.model.Expenses;
import com.projects.webdev.model.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author shubh
 */
@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseDao expenseDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthDao authDao;

    @Override
    @Transactional
    public Expenses save(String userId, Expenses expense, HttpServletRequest request) throws ExpenseException, UserException {
            if (checkSession(userId, request)) {
                Expenses newExpense = new Expenses();
                newExpense.setName(expense.getName());
                newExpense.setAmount(newExpense.getAmount());
                double toUserPercentage = +expense.getToUserSplitPercentageValue();
                double fromUserPercentage = +expense.getFromUserSplitPercentageValue();
                double amountToBePaid = 0;

                if (fromUserPercentage + toUserPercentage != 100) {
                    throw new Error("The percentage split is NOT valid!");
                }

                if (!expense.getType().isEmpty()) {
                    newExpense.setType(expense.getType());
                    switch (newExpense.getType()) {
                        case "CUSTOM":
                            amountToBePaid = this.percentage(toUserPercentage, newExpense.getAmount());
                            newExpense.setToUserSplitPercentageValue(toUserPercentage);
                            newExpense.setFromUserSplitPercentageValue(fromUserPercentage);
                            break;
                        case "FULL":
                            amountToBePaid = this.percentage(100, newExpense.getAmount());
                            newExpense.setToUserSplitPercentageValue(100);
                            newExpense.setFromUserSplitPercentageValue(0);
                            break;
                        default:
                            amountToBePaid = this.percentage(50, newExpense.getAmount());
                            newExpense.setToUserSplitPercentageValue(50);
                            newExpense.setFromUserSplitPercentageValue(50);
                            break;
                    }
                }

                System.out.println("point 2a");
                User currFromUser = userDao.get(userId);
                System.out.println("point 2b");
//            User currToUser = authDao.getByEmail(expense.getToUser().getEmail());
                User currToUser = authDao.getByEmail(expense.getToUserName());
                if(currToUser==null){
                    throw new UserException("User not found!");
                }
                System.out.println("point 2c");

                currFromUser.setAmountOwed(currFromUser.getAmountOwed() + amountToBePaid);
                System.out.println("point 2d");
                System.out.println(currFromUser.getOwedExpenses());
                if (currFromUser.getOwedExpenses() == null) {
                    Set<Expenses> expSet = new HashSet<>();
                    expSet.add(newExpense);
                    currFromUser.setOwedExpenses(expSet);
                } else {
                    currFromUser.getOwedExpenses().add(newExpense);
                }
                userDao.update(userId, currFromUser);
                System.out.println("point 2e");
                System.out.println("point 2e check");
                currToUser.setAmountOwed(currToUser.getAmountDue() + amountToBePaid);
                System.out.println("point 2f");
                if (currFromUser.getDueExpenses() == null) {
                    Set<Expenses> expSet = new HashSet<>();
                    expSet.add(newExpense);
                    currFromUser.setDueExpenses(expSet);
                } else {
                    currFromUser.getDueExpenses().add(newExpense);
                }
//                currToUser.getDueExpenses().add(newExpense);
                System.out.println("point 2g");
                userDao.update(String.valueOf(currToUser.getUserId()), currToUser);

                newExpense.setFromUser(currFromUser);
                System.out.println("point 2h");
                newExpense.setToUser(currToUser);
                System.out.println("point 2i");
//            newExpense.setFromUserName(currFromUser.getFirstName() + " " + currFromUser.getLastName());
//            newExpense.setToUserName(currToUser.getFirstName() + " " + currToUser.getLastName());
                newExpense.setFromUserName(currFromUser.getEmail());
                newExpense.setToUserName(currToUser.getEmail());
                return expenseDao.save(newExpense);
            }

        return null;
    }

//    public void updateUserWithExpense(Expenses expense) {
//        String fromUserEmail = expense.getFromUser().getEmail();
//        String toUserEmail = expense.getToUser().getEmail();
//
//        String fromUserId = expense.getFromUser().getUserId();
//        String toUserId = expense.getToUser().getUserId();
//        double fromAmountOwed = percentage(expense.getToUserSplitPercentageValue(), expense.getAmount());
//        double toAmountOwed = percentage(expense.getToUserSplitPercentageValue(), expense.getAmount());
//
//    }
    public double percentage(double percent, double total) throws ExpenseException {
        return ((percent / 100) * total);
    }

    public Boolean checkSession(String userId, HttpServletRequest request) {
        String currentUserId = (String) request.getAttribute("userId");
        System.out.println(currentUserId);
        if (userId.equals(currentUserId)) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Expenses get(String id, HttpServletRequest request) throws ExpenseException {
        Expenses getExp = expenseDao.get(id);
//        if (checkSession(String.valueOf(getExp.getId()), request)) {
        return getExp;
//        }
//        return null;
    }

    @Override
    @Transactional
    public List<Expenses> list(String userId, HttpServletRequest request) throws ExpenseException, UserException {
        if (checkSession(userId, request)) {
            List expList = new ArrayList();

            User usr = userDao.get(userId);
            Collection<Expenses> dueExpense = usr.getDueExpenses();
            Collection<Expenses> owedExpense = usr.getOwedExpenses();

            for (Expenses exp : dueExpense) {
                expList.add(exp);
            }
            for (Expenses exp : owedExpense) {
                expList.add(exp);
            }

            if (expList.size() == 0) {
//            throw new Error("No expenses, due or owed, exist for the user!");
                return null;
            }

            return expList;
        }
        return null;
//        return expenseDao.list();
    }

    @Override
    @Transactional
//    Expenses
    public Expenses update(String id, Expenses expense, HttpServletRequest request) throws ExpenseException, UserException {
        System.out.println("in upd method");
        Expenses updExpense = expenseDao.get(id);
//        if (checkSession(String.valueOf(updExpense.getToUser().getUserId()), request)) {
        updExpense.setName(expense.getName());
        double toUserPercentage = expense.getToUserSplitPercentageValue();
        double fromUserPercentage = expense.getFromUserSplitPercentageValue();
        double amountToBePaid = 0;
        System.out.println("Point 0x");
        if (fromUserPercentage + toUserPercentage != 100) {
            throw new Error("The percentage split is NOT valid!");
        }
//            updExpense.getAmount() + 
        updExpense.setAmount(updExpense.getAmount() + expense.getAmount());
        double oldAmountToBePaid = percentage(expense.getToUserSplitPercentageValue(), expense.getAmount());
        System.out.println("Point 0y");

        if (!expense.getType().isEmpty()) {
            updExpense.setType(expense.getType());
            switch (updExpense.getType()) {
                case "CUSTOM":
                    amountToBePaid = this.percentage(toUserPercentage, updExpense.getAmount());
                    updExpense.setToUserSplitPercentageValue(toUserPercentage);
                    updExpense.setFromUserSplitPercentageValue(fromUserPercentage);
                    break;
                case "FULL":
                    amountToBePaid = this.percentage(100, updExpense.getAmount());
                    updExpense.setToUserSplitPercentageValue(100);
                    updExpense.setFromUserSplitPercentageValue(0);
                    break;
                default:
                    amountToBePaid = this.percentage(50, updExpense.getAmount());
                    updExpense.setToUserSplitPercentageValue(50);
                    updExpense.setFromUserSplitPercentageValue(50);
                    break;
            }
        }
        System.out.println("Point 0z");
        User currFromUser = userDao.get(String.valueOf(updExpense.getFromUser().getUserId()));
        User currToUser = authDao.getByEmail(updExpense.getToUser().getEmail());

        System.out.println(currFromUser.getAmountOwed() + (amountToBePaid - oldAmountToBePaid));
        currFromUser.setAmountOwed(currFromUser.getAmountOwed() + (amountToBePaid - oldAmountToBePaid));
        userDao.update(String.valueOf(updExpense.getFromUser().getUserId()), currFromUser);

        System.out.println(currToUser.getAmountDue() + (amountToBePaid - oldAmountToBePaid));
        currToUser.setAmountDue(currToUser.getAmountDue() + (amountToBePaid - oldAmountToBePaid));
        userDao.update(String.valueOf(currToUser.getUserId()), currToUser);

        expenseDao.update(id, updExpense);
//        }
        return updExpense;
    }

    @Override
    @Transactional
    public void delete(String id, HttpServletRequest request) throws ExpenseException, UserException {
        Expenses exp = expenseDao.get(id);
        if (exp == null && !checkSession(String.valueOf(exp.getId()), request)) {
            throw new Error("The expense ID supplied is invalid");
        }
        System.out.println(exp.getFromUser().toString());
        System.out.println(exp.getToUser().toString());
        System.out.println(exp.toString());
        System.out.println("point 5c");
        User fromUser = exp.getFromUser();
        User toUser = exp.getToUser();

        if (fromUser == null || toUser == null) {
            throw new Error("The user details supplied are invalid");
        }

        double fromUserAmountOwed = percentage(exp.getToUserSplitPercentageValue(), exp.getAmount());
        System.out.println("point 5d");
        fromUser.setAmountOwed(fromUser.getAmountOwed() - fromUserAmountOwed);
        System.out.println("point 5e");
//        if (fromUser.getOwedExpenses() != null) {
//            System.out.println("point 5e2");
//            System.out.println(fromUser);
//
//            fromUser.getOwedExpenses().size();
//            fromUser.getOwedExpenses().remove(exp);
//        }
        System.out.println("point 5e1");
        userDao.update(String.valueOf(fromUser.getUserId()), fromUser);
        System.out.println("point 5f");
        double toUserAmountDue = percentage(exp.getToUserSplitPercentageValue(), exp.getAmount());
        System.out.println("point 5g");
        toUser.setAmountDue(toUser.getAmountDue() - toUserAmountDue);
//        if (toUser.getDueExpenses() != null) {
//            toUser.getDueExpenses().size();
//            toUser.getDueExpenses().remove(exp);
//        }
        System.out.println("point 5h");
        userDao.update(String.valueOf(toUser.getUserId()), toUser);
        expenseDao.delete(id);
    }

}
