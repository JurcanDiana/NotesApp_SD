package bll.validators;

import model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailAddressValidator implements Validator<User> {

    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";

    @Override
    public boolean validate(User user) {

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(user.getEmail());

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid email address");
        }

        return true;
    }
}
