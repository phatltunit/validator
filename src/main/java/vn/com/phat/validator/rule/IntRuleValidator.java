package vn.com.phat.validator.rule;

import vn.com.phat.validator.context.ValidationContext;
import vn.com.phat.validator.ValidationError;

public interface IntRuleValidator {
    ValidationError validate(int value, ValidationContext<?,?> context, String fieldName);
}