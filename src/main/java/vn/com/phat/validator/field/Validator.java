package vn.com.phat.validator.field;

import vn.com.phat.validator.ValidationError;
import vn.com.phat.validator.context.ValidationContext;

import java.util.List;

/**
 * To avoid the performance costs of autoboxing, I implemented specialized interfaces for primitive types
 *
 */
public interface Validator<E> {
    List<ValidationError> validate(E data, ValidationContext<?,?> context);
}
