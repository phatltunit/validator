package vn.com.phat.validator.field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import vn.com.phat.validator.rule.LongRuleValidator;
import vn.com.phat.validator.context.ValidationContext;
import vn.com.phat.validator.ValidationError;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToLongFunction;

@Getter
@AllArgsConstructor
public class LongFieldValidator<E> implements Validator<E> {

    private final String fieldName;
    private final List<LongRuleValidator> rules;
    private final ToLongFunction<E> getter;

    public List<ValidationError> validate(E data, ValidationContext<?,?> context){
        if(rules == null) return List.of();
        long value = getter.applyAsLong(data);
        
        List<ValidationError> results = new ArrayList<>(rules.size());
        for (LongRuleValidator rule : rules) {
            ValidationError res = rule.validate(value, context, fieldName);
            if (res != null) {
                results.add(res);
            }
        }
        return results;
    }
}
