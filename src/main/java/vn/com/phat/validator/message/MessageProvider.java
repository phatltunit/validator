package vn.com.phat.validator.message;

public interface MessageProvider {
    String getMessage(String template, Object... args);
}
