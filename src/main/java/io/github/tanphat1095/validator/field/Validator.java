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

package io.github.tanphat1095.validator.field;

import io.github.tanphat1095.validator.ValidationError;
import io.github.tanphat1095.validator.context.ValidationContext;

import java.util.List;

/**
 * To avoid the performance costs of autoboxing, I implemented specialized interfaces for primitive types.
 *
 * @author PhatLT
 * @since 1.0.0
 */
public interface Validator<E> {
    List<ValidationError> validate(E data, ValidationContext<?,?> context);
}
