/*
 * Copyright 2026 PhatLT.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.tanphat1095.validator.common.rule;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author PhatLT
 * @since 1.0.0
 */
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
