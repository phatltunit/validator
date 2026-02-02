package vn.com.phat.validator.rule;

import vn.com.phat.validator.context.ValidationContext;
import vn.com.phat.validator.ValidationError;

public interface LongRuleValidator {
    ValidationError validate(long value, ValidationContext<?,?> context, String fieldName);
}
