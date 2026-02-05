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

package io.github.tanphat1095.validator;

/**
 * @author PhatLT
 * @since 1.0.0
 */
public record ValidationError(boolean valid, String targetName, String message, String errorCode) {
    public static ValidationError fail(String targetName, String message) {
        return new ValidationError(false, targetName, message, null);
    }

    public static ValidationError fail(String targetName, String message, String errorCode) {
        return new ValidationError(false, targetName, message, errorCode);
    }
}
