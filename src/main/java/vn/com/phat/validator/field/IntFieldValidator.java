package vn.com.phat.validator.field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import vn.com.phat.validator.rule.IntRuleValidator;
import vn.com.phat.validator.context.ValidationContext;
import vn.com.phat.validator.ValidationError;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

@Getter
@AllArgsConstructor
public class IntFieldValidator<E> implements Validator<E> {

    private final String fieldName;
    private final List<IntRuleValidator> rules;
    private final ToIntFunction<E> getter;

    public List<ValidationError> validate(E data, ValidationContext<?,?> context){
        if(rules == null) return List.of();
        int value = getter.applyAsInt(data);
        
        List<ValidationError> results = new ArrayList<>(rules.size());
        for (IntRuleValidator rule : rules) {
            ValidationError res = rule.validate(value, context, fieldName);
            if (res != null) {
                results.add(res);
            }
        }
        return results;
    }
}
