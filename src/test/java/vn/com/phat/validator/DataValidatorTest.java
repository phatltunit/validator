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

import org.junit.jupiter.api.Test;
import vn.com.phat.validator.common.rule.CommonRules;
import vn.com.phat.validator.field.*;
import vn.com.phat.validator.rule.DoubleRuleValidator;
import vn.com.phat.validator.rule.IntRuleValidator;
import vn.com.phat.validator.rule.LongRuleValidator;
import vn.com.phat.validator.rule.RuleValidator;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author PhatLT
 * @since 1.0.0
 */
class DataValidatorTest {

    @Test
    void shouldValidateCorrectData() {
        // Given
        TestObject validObj = new TestObject("John Doe", 25, "Senior Developer", 1000L, 99.9);
        List<TestObject> data = List.of(validObj);

        var maxLength10 = CommonRules.maxStringLength(10);
        IntRuleValidator ageMoreThan18 = (value, context, fieldName) -> 
            value >= 18 ? null : ValidationError.fail(fieldName, "Too young");
        LongRuleValidator positiveId = (value, context, fieldName) -> 
            value > 0 ? null : ValidationError.fail(fieldName, "Negative ID");
        DoubleRuleValidator positivePrice = (value, context, fieldName) -> 
            value > 0.0 ? null : ValidationError.fail(fieldName, "Negative Price");

        Validator<TestObject> nameValidator = new FieldValidator<>("name", List.of(CommonRules.required(), maxLength10), TestObject::getName);
        Validator<TestObject> ageValidator = new IntFieldValidator<>("age", List.of(ageMoreThan18), TestObject::getAge);
        Validator<TestObject> idValidator = new LongFieldValidator<>("id", List.of(positiveId), TestObject::getId);
        Validator<TestObject> priceValidator = new DoubleFieldValidator<>("price", List.of(positivePrice), TestObject::getPrice);

        DataValidator<TestObject> dataValidator = new DataValidator<>(List.of(nameValidator, ageValidator, idValidator, priceValidator), null);

        // When
        dataValidator.validate(data);

        assertThat(validObj.getValidationResult()).isNullOrEmpty();
    }

    @Test
    void shouldSupportLazyStreamValidation() {
        TestObject validObj = new TestObject("John Doe", 25, "Senior Developer", 1000L, 99.9);
        Stream<TestObject> dataStream = Stream.of(validObj);

        IntRuleValidator ageMoreThan18 = (value, context, fieldName) -> 
            value >= 18 ? null : ValidationError.fail(fieldName, "Too young");

        Validator<TestObject> ageValidator = new IntFieldValidator<>("age", List.of(ageMoreThan18), TestObject::getAge);
        DataValidator<TestObject> dataValidator = new DataValidator<>(List.of(ageValidator), null);

        Stream<TestObject> validatedStream = dataValidator.validate(dataStream);
        
        assertThat(validObj.getValidationResult()).isNullOrEmpty();

        validatedStream.forEach(e -> {});

        assertThat(validObj.getValidationResult()).isNullOrEmpty();
    }

    @Test
    void shouldHandleCustomLambdaRules() {
        TestObject obj = new TestObject("SecretUser", 30, "No entry", 1L, 10.0);
        List<TestObject> data = List.of(obj);

        RuleValidator<String> mustContainSecret = (value, context, fieldName) ->
            (value != null && value.contains("Secret")) 
                ? null
                : ValidationError.fail(fieldName, "Must contain 'Secret'");

        IntRuleValidator mustBeEven = (value, context, fieldName) ->
            (value % 2 == 0) 
                ? null
                : ValidationError.fail(fieldName, "Must be even");

        Validator<TestObject> nameValidator = new FieldValidator<>("name", List.of(mustContainSecret), TestObject::getName);
        Validator<TestObject> ageValidator = new IntFieldValidator<>("age", List.of(mustBeEven), TestObject::getAge);

        DataValidator<TestObject> dataValidator = new DataValidator<>(List.of(nameValidator, ageValidator), null);

        dataValidator.validate(data);

        assertThat(obj.getValidationResult()).isNullOrEmpty();

        obj.setName("NormalUser");
        obj.setAge(31);
        dataValidator.validate(data);
        
        assertThat(obj.getValidationResult()).hasSize(2);
        assertThat(obj.getValidationResult().get(0).message()).contains("Must contain 'Secret'");
        assertThat(obj.getValidationResult().get(1).message()).contains("Must be even");
    }

    @Test
    void shouldCaptureSpecializedValidationErrors() {
        TestObject invalidObj = new TestObject("Joe", 15, "Description", -1L, -5.5);
        List<TestObject> data = List.of(invalidObj);

        var minLength5 = CommonRules.minStringLength(5);
        IntRuleValidator ageMoreThan18 = (value, context, fieldName) -> 
            value >= 18 ? null : ValidationError.fail(fieldName, "Too young");
        LongRuleValidator positiveId = (value, context, fieldName) -> 
            value > 0 ? null : ValidationError.fail(fieldName, "Negative ID");
        DoubleRuleValidator positivePrice = (value, context, fieldName) -> 
            value > 0.0 ? null : ValidationError.fail(fieldName, "Negative Price");

        Validator<TestObject> nameValidator = new FieldValidator<>("name", List.of(minLength5), TestObject::getName);
        Validator<TestObject> ageValidator = new IntFieldValidator<>("age", List.of(ageMoreThan18), TestObject::getAge);
        Validator<TestObject> idValidator = new LongFieldValidator<>("id", List.of(positiveId), TestObject::getId);
        Validator<TestObject> priceValidator = new DoubleFieldValidator<>("price", List.of(positivePrice), TestObject::getPrice);

        DataValidator<TestObject> dataValidator = new DataValidator<>(List.of(nameValidator, ageValidator, idValidator, priceValidator), null);

        dataValidator.validate(data);

        List<ValidationError> results = invalidObj.getValidationResult();
        assertThat(results).hasSize(4);
        
        assertThat(results.stream().filter(r -> r.fieldName().equals("name")).findFirst().get().valid()).isFalse();
        assertThat(results.stream().filter(r -> r.fieldName().equals("age")).findFirst().get().valid()).isFalse();
        assertThat(results.stream().filter(r -> r.fieldName().equals("id")).findFirst().get().valid()).isFalse();
        assertThat(results.stream().filter(r -> r.fieldName().equals("price")).findFirst().get().valid()).isFalse();
    }

    @Test
    void shouldPassIfNoRulesConfigured() {
        TestObject obj = new TestObject("John Doe", 25, "Senior Developer", 1000L, 99.9);
        List<TestObject> data = List.of(obj);

        // String field with empty rules
        Validator<TestObject> stringValidator = new FieldValidator<>("name", List.of(), TestObject::getName);
        // Primitive int with null rules
        Validator<TestObject> intValidator = new IntFieldValidator<>("age", null, TestObject::getAge);
        // Primitive long with empty rules
        Validator<TestObject> longValidator = new LongFieldValidator<>("id", List.of(), TestObject::getId);
        // Primitive double with null rules
        Validator<TestObject> doubleValidator = new DoubleFieldValidator<>("price", null, TestObject::getPrice);
        
        DataValidator<TestObject> dataValidator = new DataValidator<>(
            List.of(stringValidator, intValidator, longValidator, doubleValidator), 
            null
        );

        dataValidator.validate(data);

        assertThat(obj.getValidationResult()).isNullOrEmpty();
    }

    @Test
    void shouldPassIfOptionalFieldIsNull() {
        // Given: Name is null, but no 'required' rule is set
        TestObject obj = new TestObject(null, 25, "Senior Developer", 1000L, 99.9);
        List<TestObject> data = List.of(obj);

        var maxLength10 = CommonRules.maxStringLength(10);
        Validator<TestObject> nameValidator = new FieldValidator<>("name", List.of(maxLength10), TestObject::getName);
        DataValidator<TestObject> dataValidator = new DataValidator<>(List.of(nameValidator), null);

        // When
        dataValidator.validate(data);

        // Then
        assertThat(obj.getValidationResult()).isNullOrEmpty();
    }

    @Test
    void shouldHandleNullData() {
        DataValidator<TestObject> dataValidator = new DataValidator<>(List.of(), null);
        
        // Null iterable
        assertThat(dataValidator.validate((List<TestObject>) null)).isEmpty();
        
        // Null stream
        assertThat(dataValidator.validate((Stream<TestObject>) null)).isEmpty();
        
        // Null single object
        assertThat(dataValidator.validate((TestObject) null)).isNull();
    }

    @Test
    void shouldHandleNullItemsInData() {
        TestObject validObj = new TestObject("John Doe", 25, "Senior Developer", 1000L, 99.9);
        List<TestObject> data = new java.util.ArrayList<>();
        data.add(validObj);
        data.add(null);
        
        Validator<TestObject> nameValidator = new FieldValidator<>("name", List.of(CommonRules.required()), TestObject::getName);
        DataValidator<TestObject> dataValidator = new DataValidator<>(List.of(nameValidator), null);

        // When
        dataValidator.validate(data);

        // Then
        assertThat(data).hasSize(2);
        assertThat(data.get(0).getValidationResult()).isNullOrEmpty();
        assertThat(data.get(1)).isNull();
    }

    @Test
    void shouldHandleNullItemsInStream() {
        TestObject validObj = new TestObject("John Doe", 25, "Senior Developer", 1000L, 99.9);
        Stream<TestObject> dataStream = Stream.of(validObj, null);
        
        Validator<TestObject> nameValidator = new FieldValidator<>("name", List.of(CommonRules.required()), TestObject::getName);
        DataValidator<TestObject> dataValidator = new DataValidator<>(List.of(nameValidator), null);

        // When
        List<TestObject> results = dataValidator.validate(dataStream).toList();

        // Then
        assertThat(results).hasSize(2);
        assertThat(results.get(0).getValidationResult()).isNullOrEmpty();
        assertThat(results.get(1)).isNull();
    }

    @lombok.Getter
    @lombok.Setter
    @lombok.AllArgsConstructor
    static class TestObject implements Validatable {
        private String name;
        private int age;
        private String desc;
        private long id;
        private double price;
        private List<ValidationError> validationResult;

        public TestObject(String name, int age, String desc, long id, double price) {
            this.name = name;
            this.age = age;
            this.desc = desc;
            this.id = id;
            this.price = price;
        }
    }
}
