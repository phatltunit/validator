package vn.com.phat.validator.common.rule;

import lombok.AllArgsConstructor;
import vn.com.phat.validator.ValidationError;
import vn.com.phat.validator.context.ValidationContext;
import vn.com.phat.validator.rule.RuleValidator;

@AllArgsConstructor
public class RuleMinStringLength implements RuleValidator<String> {

    private final int minLength;

    @Override
    public ValidationError validate(String value, ValidationContext<?, ?> context, String fieldName) {
        if(value != null && value.length() < minLength)
            return ValidationError.fail(fieldName, "Field "+ fieldName + " should not less than "+ minLength+ " characters");
        return null;
    }
}
