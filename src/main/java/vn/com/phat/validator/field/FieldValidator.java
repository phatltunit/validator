package vn.com.phat.validator.field;

import vn.com.phat.validator.rule.RuleValidator;
import vn.com.phat.validator.context.ValidationContext;
import vn.com.phat.validator.ValidationError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public record FieldValidator<T, E>(String fieldName, List<RuleValidator<T>> rules, Function<E, T> getter) implements Validator<E> {

    public List<ValidationError> validate(E data, ValidationContext<?, ?> context) {
        if (rules == null || rules.isEmpty())
            return Collections.emptyList();
        T value = getter.apply(data);

        List<ValidationError> results = null;
        for (RuleValidator<T> rule : rules) {
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
