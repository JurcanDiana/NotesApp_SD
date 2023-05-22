package bll.validators;

public interface Validator<T> {
    public boolean validate(T t);
}

