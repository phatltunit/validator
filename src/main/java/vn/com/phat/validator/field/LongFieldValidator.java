package vn.com.phat.validator.field;

import vn.com.phat.validator.rule.LongRuleValidator;
import vn.com.phat.validator.context.ValidationContext;
import vn.com.phat.validator.ValidationError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.ToLongFunction;

public record LongFieldValidator<E>(String fieldName, List<LongRuleValidator> rules, ToLongFunction<E> getter) implements Validator<E> {

    public List<ValidationError> validate(E data, ValidationContext<?, ?> context) {
        if (rules == null || rules.isEmpty())
            return Collections.emptyList();
        long value = getter.applyAsLong(data);

        List<ValidationError> results = null;
        for (LongRuleValidator rule : rules) {
            ValidationError res = rule.validate(value, context, fieldName);
            if (res != null) {
                if (results == null) {
                    results = new ArrayList<>();
                }
                results.add(res);
            }
        }
        return results;
    }
}
