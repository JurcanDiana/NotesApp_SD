package bll;

import bll.validators.EmailAddressValidator;
import bll.validators.PasswordValidator;
import bll.validators.Validator;
import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class UserBLL {

    List<Validator<User>> validators;
    private UserDAO userDAO;

    public UserBLL() {
        validators = new ArrayList<Validator<User>>();
        validators.add((Validator<User>) new EmailAddressValidator());
        validators.add((Validator<User>) new PasswordValidator());

        userDAO = new UserDAO();
    }

    public User findUserById(int id) {
        User user = userDAO.findById(id);
        if(user == null) {
            throw new NoSuchElementException("The user with id "
                    + id + " could not be found");
        }
        return user;
    }

    public User findByName(String username) {
        User user = userDAO.findByName(username);
        if(user == null) {
            throw new NoSuchElementException("The user with username "
                    + username + " could not be found");
        }
        return user;
    }

    public ArrayList<User> findAll() {
        ArrayList<User> users = userDAO.findAll();
        if(users == null) {
            throw new NoSuchElementException("The list of users is empty!");
        }
        return users;
    }

    public int insertUser(User user) {
        try {
            checkValidators(user);
            return userDAO.insert(user);
        } catch (IllegalArgumentException e) {
            JFrame popup = new JFrame();
            JOptionPane.showMessageDialog(popup, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }


    public boolean update(User user, int id) {
        checkValidators(user);
        return userDAO.update(user, id);
    }

    public boolean delete(int id) {
        return userDAO.delete(id);
    }

    public List<User> selectAll() {
        return userDAO.findAll();
    }

    public void viewAllUsers(DefaultTableModel model, JTable table) {

        String[] columns = {"userID", "username", "firstName", "lastName", "email" };

        for(String column: columns) {
            model.addColumn(column);
        }

        List<User> users = selectAll();

        for (User user: users) {
            model.addRow(new Object[]{String.valueOf(user.getId()),
                    String.valueOf(user.getUsername()),
                    String.valueOf(user.getFirstName()),
                    String.valueOf(user.getLastName()),
                    String.valueOf(user.getEmail()),
            });
        }

        table.setModel(model);
    }

    public boolean checkValidators(User user) {
        for (Validator<User> validator: validators) {
            if(!validator.validate(user)) {
                return false;
            }
        }
        return true;
    }
}
