package vn.com.phat.validator;

import java.util.List;

public interface Validatable {

    void setValidationResult(List<ValidationError> validationResult);
    List<ValidationError> getValidationResult();

}
