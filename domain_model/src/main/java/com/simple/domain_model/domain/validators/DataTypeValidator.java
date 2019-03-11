package com.simple.domain_model.domain.validators;

import com.simple.domain_model.domain.Attribute;
import com.simple.domain_model.domain.DataType;
import com.simple.domain_model.domain.Property;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DataTypeValidator implements ConstraintValidator<CheckDataType, Object> {

   private DataType type;

   @Override
   public void initialize(CheckDataType constraint) {
      type = constraint.type();
   }

   public boolean isValid(Object value, ConstraintValidatorContext context) {
      if (value == null) return false;
      if (value instanceof Property) {return ((Property) value).getType() == type;}
      if (value instanceof Attribute) {return ((Attribute) value).getType() == type;}
      return false;
   }
}
