package bll.validators;

import model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements Validator<User> {

    private static final int MIN_LENGTH = 8;

    @Override
    public boolean validate(User user) {

        if (user.getPassword().length() < MIN_LENGTH) {
            throw new IllegalArgumentException("Password must be at least " + MIN_LENGTH + " characters long");
        }

        return true;
    }
}

