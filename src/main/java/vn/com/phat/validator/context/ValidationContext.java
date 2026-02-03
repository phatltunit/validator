package vn.com.phat.validator.context;

import vn.com.phat.validator.message.MessageProvider;

import java.util.Map;

public interface ValidationContext<K,V> extends Map<K,V>{
    void setMessageProvider(MessageProvider provider);
    MessageProvider getMessageProvider();
}
