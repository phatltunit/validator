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

package vn.com.phat.validator.message;

import org.junit.jupiter.api.Test;
import vn.com.phat.validator.ValidationError;
import vn.com.phat.validator.common.rule.CommonRules;
import vn.com.phat.validator.common.rule.RuleRequired;
import vn.com.phat.validator.context.DefaultValidationContext;
import vn.com.phat.validator.context.ValidationContext;

import java.text.MessageFormat;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author PhatLT
 * @since 1.0.0
 */
class MessageProviderTest {

    @Test
    void defaultMessageProviderShouldWork() {
        RuleRequired<String> rule = CommonRules.required();
        ValidationContext<String, Object> context = new DefaultValidationContext();
        
        ValidationError error = rule.validate(null, context, "username");
        
        assertThat(error).isNotNull();
        assertThat(error.message()).isEqualTo("Field username is required.");
    }

    @Test
    void customMessageViaFluentApiShouldHaveHighestPriority() {
        RuleRequired<String> rule = CommonRules.<String>required().withMessage("Custom hardcoded message for username");

        ValidationContext<String, Object> context = new DefaultValidationContext();
        context.setMessageProvider(new DefaultMessageProvider());

        ValidationError error = rule.validate(null, context, "username");

        assertThat(error).isNotNull();
        assertThat(error.message()).isEqualTo("Custom hardcoded message for username");
    }

    @Test
    void defaultMessageProviderShouldReturnDefaultMessageWhenNoCustomMessageSet() {
        RuleRequired<String> rule = CommonRules.required();
        ValidationContext<String, Object> context = new DefaultValidationContext();
        context.setMessageProvider(new DefaultMessageProvider());

        ValidationError error = rule.validate(null, context, "email");

        assertThat(error).isNotNull();
        assertThat(error.message()).isEqualTo("Field email is required.");
    }

    @Test
    void customMessageProviderShouldWork() {
        RuleRequired<String> rule = CommonRules.<String>required().withErrorCode("ERR_REQUIRED");
        ValidationContext<String, Object> context = new DefaultValidationContext();
        context.setMessageProvider((template, args) -> MessageFormat.format("Test {0}", args));
        ValidationError error = rule.validate(null, context, "username");
        assertThat(error).isNotNull();
        assertThat(error.message()).isEqualTo("Test username");
        assertThat(error.errorCode()).isEqualTo("ERR_REQUIRED");
    }

    @Test
    void defaultMessageProviderShouldHandleNullTemplate() {
        DefaultMessageProvider provider = new DefaultMessageProvider();
        assertThat(provider.getMessage(null, "some-arg")).isNull();
    }
}
