package vn.com.phat.validator.field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import vn.com.phat.validator.rule.RuleValidator;
import vn.com.phat.validator.context.ValidationContext;
import vn.com.phat.validator.ValidationError;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Getter
@AllArgsConstructor
public class FieldValidator<T, E> implements Validator<E> {

    private final String fieldName;
    private final List<RuleValidator<T>> rules;
    private final Function<E,T> getter;

    public List<ValidationError> validate(E data, ValidationContext<?,?> context){
        if(rules == null) return List.of();
        T value = getter.apply(data);
        
        List<ValidationError> results = new ArrayList<>(rules.size());
        for (RuleValidator<T> rule : rules) {
            ValidationError res = rule.validate(value, context, fieldName);
            if (res != null) {
                results.add(res);
            }
        }
        return results;
    }
}
