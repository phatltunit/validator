package vn.com.phat.validator;

public record ValidationError(boolean valid, String fieldName, String message, String errorCode) {
    public static ValidationError fail(String fieldName, String message) {
        return new ValidationError(false, fieldName, message, null);
    }

    public static ValidationError fail(String fieldName, String message, String errorCode) {
        return new ValidationError(false, fieldName, message, errorCode);
    }
}
