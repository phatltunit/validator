package vn.com.phat.validator.common.rule;

import vn.com.phat.validator.context.ValidationContext;
import vn.com.phat.validator.ValidationError;
import vn.com.phat.validator.rule.AbstractRule;


public class RuleRequired<T> extends AbstractRule<T, RuleRequired<T>> {

    @Override
    public ValidationError validate(T value, ValidationContext<?,?> context, String fieldName) {
        if(value == null) {
            return fail(context, fieldName, "Field {0} is required.", fieldName);
        }
        return null;
    }
}
