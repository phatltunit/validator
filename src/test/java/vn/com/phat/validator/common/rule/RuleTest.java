package vn.com.phat.validator.common.rule;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RuleTest {

    @Test
    void ruleRequiredShouldWork() {
        RuleRequired<String> rule = CommonRules.required();
        
        assertThat(rule.validate("John Doe", null, "name")).isNull();
        assertThat(rule.validate(null, null, "name").valid()).isFalse();
    }

    @Test
    void ruleMaxStringLengthShouldWork() {
        RuleMaxStringLength rule = CommonRules.maxStringLength(5);
        
        assertThat(rule.validate("12345", null, "code")).isNull();
        assertThat(rule.validate("123456", null, "code").valid()).isFalse();
        assertThat(rule.validate(null, null, "code")).isNull(); // Should be OK, handled by RuleRequired
    }

    @Test
    void ruleMinStringLengthShouldWork() {
        RuleMinStringLength rule = CommonRules.minStringLength(5);
        
        assertThat(rule.validate("12345", null, "code")).isNull();
        assertThat(rule.validate("1234", null, "code").valid()).isFalse();
        assertThat(rule.validate(null, null, "code")).isNull(); // Should be null, handled by RuleRequired
    }
}
