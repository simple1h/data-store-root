package com.simple.domain_model.domain.validators;

import com.simple.domain_model.domain.DataType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DataTypeValidator.class)
public @interface CheckDataType {

    String message() default "{Data type not supported}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    DataType type();
}
