package vn.com.phat.validator.common.rule;

import lombok.AllArgsConstructor;
import vn.com.phat.validator.ValidationError;
import vn.com.phat.validator.context.ValidationContext;
import vn.com.phat.validator.rule.RuleValidator;

import java.text.MessageFormat;

@AllArgsConstructor
public class RuleMinStringLength implements RuleValidator<String> {

    private final int minLength;

    @Override
    public ValidationError validate(String value, ValidationContext<?, ?> context, String fieldName) {
        if(value != null && value.length() < minLength)
            return ValidationError.fail(fieldName, MessageFormat.format("Field {0} should not less than {1} characters", fieldName, minLength));
        return null;
    }
}
