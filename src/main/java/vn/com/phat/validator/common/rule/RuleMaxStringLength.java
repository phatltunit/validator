package vn.com.phat.validator.common.rule;

import lombok.AllArgsConstructor;
import vn.com.phat.validator.context.ValidationContext;
import vn.com.phat.validator.ValidationError;


import vn.com.phat.validator.rule.AbstractRule;

@AllArgsConstructor
public class RuleMaxStringLength extends AbstractRule<String, RuleMaxStringLength> {

    private final int maxLength;

    @Override
    public ValidationError validate(String value, ValidationContext<?,?> context, String fieldName) {
        if(value != null && value.length() > maxLength)
            return fail(context ,fieldName, "Field {0} should not more than {1} characters", fieldName, maxLength);
        return null;
    }
}
