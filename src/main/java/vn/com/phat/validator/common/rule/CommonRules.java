package vn.com.phat.validator.common.rule;

public class CommonRules {

    private CommonRules(){}

    public static <T> RuleRequired<T> required(){
        return new RuleRequired<>();
    }

    public static RuleMaxStringLength maxStringLength(int length){
        return new RuleMaxStringLength(length);
    }

    public static RuleMinStringLength minStringLength(int length){
        return new RuleMinStringLength(length);
    }

}
