package vn.com.phat.validator.field;

import vn.com.phat.validator.rule.DoubleRuleValidator;
import vn.com.phat.validator.context.ValidationContext;
import vn.com.phat.validator.ValidationError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.ToDoubleFunction;

public record DoubleFieldValidator<E>(String fieldName, List<DoubleRuleValidator> rules, ToDoubleFunction<E> getter) implements Validator<E> {

    public List<ValidationError> validate(E data, ValidationContext<?, ?> context) {
        if (rules == null || rules.isEmpty())
            return Collections.emptyList();
        double value = getter.applyAsDouble(data);

        List<ValidationError> results = null;
        for (DoubleRuleValidator rule : rules) {
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
