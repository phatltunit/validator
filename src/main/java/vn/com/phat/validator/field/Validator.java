package vn.com.phat.validator.field;

import vn.com.phat.validator.ValidationError;
import vn.com.phat.validator.context.ValidationContext;

import java.util.List;

public interface Validator<E> {
    List<ValidationError> validate(E data, ValidationContext<?,?> context);
}
