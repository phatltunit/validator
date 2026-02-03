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

package vn.com.phat.validator.common.rule;

import lombok.AllArgsConstructor;
import vn.com.phat.validator.context.ValidationContext;
import vn.com.phat.validator.ValidationError;
import vn.com.phat.validator.rule.AbstractRule;

/**
 * @author PhatLT
 * @since 1.0.0
 */
@AllArgsConstructor
public class RuleMaxStringLength extends AbstractRule<String, RuleMaxStringLength> {

    private final int maxLength;

    @Override
    public ValidationError validate(String value, ValidationContext<?,?> context, String fieldName) {
        if(value != null && value.length() > maxLength)
            return fail(context ,fieldName, "Field {0} should not more than {1} characters", fieldName, maxLength);
        return null;
    }
}
