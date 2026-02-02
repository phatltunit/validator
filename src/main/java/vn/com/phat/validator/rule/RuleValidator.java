package vn.com.phat.validator.rule;

import vn.com.phat.validator.context.ValidationContext;
import vn.com.phat.validator.ValidationError;

@FunctionalInterface
public interface RuleValidator<T> {
    ValidationError validate(T value, ValidationContext<?,?> context, String fieldName);
}
