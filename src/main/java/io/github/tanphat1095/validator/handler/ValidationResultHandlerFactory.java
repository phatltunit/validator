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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Factory class for managing and providing ValidationResultHandlers.
 *
 * @author Le Tan Phat
 * @since 1.0.0
 */
public class ValidationResultHandlerFactory {
    private final List<ValidationResultHandler> handlers = new ArrayList<>();

    public ValidationResultHandlerFactory() {
        this.handlers.add(new MapValidationResultHandler());
        this.handlers.add(new DefaultValidationResultHandler());
    }

    public ValidationResultHandlerFactory(List<ValidationResultHandler> customHandlers) {
        this();
        if (customHandlers != null) {
            this.handlers.addAll(0, customHandlers);
        }
    }

    /**
     * Finds the first handler that supports the given class.
     *
     * @param clazz the class of the object to be handled
     * @return an Optional containing the handler, or empty if none found
     */
    public Optional<ValidationResultHandler> getHandler(Class<?> clazz) {
        if (clazz == null) return Optional.empty();
        return handlers.stream()
                .filter(h -> h.support(clazz))
                .findFirst();
    }
}
