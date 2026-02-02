package vn.com.phat.validator.common.rule;

import vn.com.phat.validator.rule.RuleValidator;
import vn.com.phat.validator.context.ValidationContext;
import vn.com.phat.validator.ValidationError;

public class RuleRequired<T> implements RuleValidator<T> {

    @Override
    public ValidationError validate(T value, ValidationContext<?,?> context, String fieldName) {
        if(value == null) return ValidationError.fail(fieldName, "Field "+ fieldName + " is required.");
        return null;
    }
}
