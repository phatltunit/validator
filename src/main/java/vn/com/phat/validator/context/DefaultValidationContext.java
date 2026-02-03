package vn.com.phat.validator.context;

import vn.com.phat.validator.message.MessageProvider;

import java.util.HashMap;

public class DefaultValidationContext extends HashMap<String, Object> implements ValidationContext<String,Object>{

    private static final String MESSAGE_PROVIDER = "MESSAGE_PROVIDER";

    @Override
    public void setMessageProvider(MessageProvider provider) {
        put(MESSAGE_PROVIDER, provider);
    }

    @Override
    public MessageProvider getMessageProvider() {
        return (MessageProvider) get(MESSAGE_PROVIDER);
    }
}
