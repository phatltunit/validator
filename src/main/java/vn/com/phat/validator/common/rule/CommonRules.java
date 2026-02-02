package vn.com.phat.validator.common.rule;

import java.util.HashMap;
import java.util.Map;

public class CommonRules {

    private CommonRules(){}

    private static final RuleRequired<Object> REQUIRED = new RuleRequired<>();
    private static final Map<Integer, RuleMaxStringLength> MAX_LENGTH_CACHE = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> RuleRequired<T> required(){
        return (RuleRequired<T>) REQUIRED;
    }

    public static RuleMaxStringLength maxStringLength(int length){
        RuleMaxStringLength rule = MAX_LENGTH_CACHE.get(length);
        if(rule == null){
            rule = new RuleMaxStringLength(length);
            MAX_LENGTH_CACHE.put(length, rule);
        }
        return rule;
    }

}
