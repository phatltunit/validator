package vn.com.phat.validator.message;

import java.text.MessageFormat;

public class DefaultMessageProvider implements MessageProvider{

    @Override
    public String getMessage(String template, Object... args) {
        return template == null ? null : MessageFormat.format(template, args);
    }
}
