package vn.com.phat.validator.common.rule;

import lombok.AllArgsConstructor;
import vn.com.phat.validator.rule.RuleValidator;
import vn.com.phat.validator.context.ValidationContext;
import vn.com.phat.validator.ValidationError;

import java.text.MessageFormat;

@AllArgsConstructor
public class RuleMaxStringLength implements RuleValidator<String> {

    private final int maxLength;

    @Override
    public ValidationError validate(String value, ValidationContext<?,?> context, String fieldName) {
        if(value != null && value.length() > maxLength)
            return ValidationError.fail(fieldName, MessageFormat.format("Field {0} should not more than {1} characters", fieldName, maxLength));
        return null;
    }
}
