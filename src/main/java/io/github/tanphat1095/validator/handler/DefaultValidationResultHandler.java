/*
 * Copyright 2026 Le Tan Phat.
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

package io.github.tanphat1095.validator.handler;

import io.github.tanphat1095.validator.Validatable;
import io.github.tanphat1095.validator.ValidationError;

import java.util.List;

/**
 * Default implementation of ValidationResultHandler that handles Validatable objects.
 *
 * @author Le Tan Phat
 * @since 1.0.0
 */
public class DefaultValidationResultHandler implements ValidationResultHandler {

    @Override
    public <T> void handle(T validatable, List<ValidationError> errors) {
        if (validatable instanceof Validatable validatable1) {
            validatable1.setValidationResult(errors);
        }
    }

    @Override
    public boolean support(Class<?> clazz) {
        return Validatable.class.isAssignableFrom(clazz);
    }
}
