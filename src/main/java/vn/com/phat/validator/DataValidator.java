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

package vn.com.phat.validator;

import vn.com.phat.validator.context.ValidationContext;
import vn.com.phat.validator.field.Validator;
import vn.com.phat.validator.handler.ValidationResultHandler;
import vn.com.phat.validator.handler.ValidationResultHandlerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author PhatLT
 * @since 1.0.0
 */
public class DataValidator<E> {

    private final List<Validator<E>> fields;
    private final ValidationContext<?,?> context;
    private final ValidationResultHandlerFactory handlerFactory;

    public DataValidator(List<Validator<E>> fields, ValidationContext<?,?> context){
        this.fields = fields;
        this.context = context;
        this.handlerFactory = new ValidationResultHandlerFactory();
    }

    public DataValidator(List<Validator<E>> fields, ValidationContext<?,?> context, List<ValidationResultHandler> customHandlers){
        this.fields = fields;
        this.context = context;
        this.handlerFactory = new ValidationResultHandlerFactory(customHandlers);
    }

    public Iterable<E> validate(Iterable<E> data){
        if(data == null) return List.of();
        
        for (E e : data) {
            validate(e);
        }
        return data;
    }

    /**
     * Supports true lazy loading by wrapping the validation logic into a Stream pipeline.
     * Use this for extremely large datasets (10M+ records) where you don't want to load everything into RAM.
     */
    public Stream<E> validate(Stream<E> dataStream) {
        if (dataStream == null) return Stream.empty();
        return dataStream.map(this::validate);
    }

    public E validate(E e) {
        if (e == null)
            return null;
        List<ValidationError> totalResults = null;
        for (Validator<E> field : fields) {
            List<ValidationError> fieldResults = field.validate(e, context);
            if (fieldResults != null && !fieldResults.isEmpty()) {
                if (totalResults == null) {
                    totalResults = new ArrayList<>();
                }
                totalResults.addAll(fieldResults);
            }
        }
        handleResult(e, totalResults);
        return e;
    }

    private void handleResult(Object object, List<ValidationError> errors){
        if(object == null) return;
        handlerFactory.getHandler(object.getClass())
                .ifPresent(handler -> handler.handle(object, errors));
    }
}
