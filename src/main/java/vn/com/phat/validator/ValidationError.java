package vn.com.phat.validator;

public record ValidationError(boolean valid, String fieldName, String message) {
    public static ValidationError fail(String fieldName, String message) {
        return new ValidationError(false, fieldName, message);
    }
}
