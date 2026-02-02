package vn.com.phat.validator;

import lombok.AllArgsConstructor;
import vn.com.phat.validator.context.ValidationContext;
import vn.com.phat.validator.field.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@AllArgsConstructor
public class DataValidator<E extends ValidationMetaData> {

    private final List<Validator<E>> fields;
    private final ValidationContext<?,?> context;

    public Iterable<E> validate(Iterable<E> data){
        if(data == null) return List.of();
        
        for (E e : data) {
            validateSingle(e);
        }
        return data;
    }

    /**
     * Supports true lazy loading by wrapping the validation logic into a Stream pipeline.
     * Use this for extremely large datasets (10M+ records) where you don't want to load everything into RAM.
     */
    public Stream<E> validate(Stream<E> dataStream) {
        if (dataStream == null) return Stream.empty();
        return dataStream.peek(this::validateSingle);
    }

    private void validateSingle(E e) {
        List<ValidationError> totalResults = null;
        for (Validator<E> field : fields) {
            List<ValidationError> fieldResults = field.validate(e, context);
            if (fieldResults != null && !fieldResults.isEmpty()) {
                if (totalResults == null) {
                    totalResults = new ArrayList<>();
                }
                totalResults.addAll(fieldResults);
            }
        }
        e.setValidationResult(totalResults);
    }

}
