package vn.com.phat.validator.rule;

import vn.com.phat.validator.ValidationError;
import vn.com.phat.validator.context.ValidationContext;
import vn.com.phat.validator.message.DefaultMessageProvider;
import vn.com.phat.validator.message.MessageProvider;

import java.util.Optional;

public abstract class AbstractRule<T, R extends AbstractRule<T, R>> implements RuleValidator<T> {
    protected String customMessage;
    protected String errorCode;

    @SuppressWarnings("unchecked")
    public R withMessage(String message) {
        this.customMessage = message;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R withErrorCode(String code) {
        this.errorCode = code;
        return (R) this;
    }

    protected ValidationError fail(ValidationContext<?,?> context, String fieldName, String defaultTemplate, Object... args) {
        MessageProvider messageProvider = new DefaultMessageProvider();
        if(context != null)
            messageProvider = Optional.ofNullable(context.getMessageProvider()).orElse(messageProvider);
        String template = (customMessage != null) ? customMessage : defaultTemplate;
        return ValidationError.fail(fieldName, messageProvider.getMessage(template, args), errorCode);
    }
}
