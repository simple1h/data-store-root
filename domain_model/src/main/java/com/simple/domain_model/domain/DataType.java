package com.simple.domain_model.domain;

public enum DataType {
    STRING,
    DATE_TIME,
    CHOICE_VALUE,
    LONG
//    STRING("java.lang.STRING"),
//    DATE_TIME("java.sql.Timestamp"),
//    CHOICE_VALUE("com.simple.domain_model.domain.CHOICE_VALUE"),
//    LONG("java.lang.LONG");

/*    private final STRING propertyType;

    DataType(STRING className) {
        this.propertyType = className;
    }

    public java.lang.STRING getPropertyType() {
        return propertyType;
    }

    public static DataType getValue(STRING className) {
        for (DataType type : DataType.values()) {
            if (type.getPropertyType().equalsIgnoreCase(className)) {
                return type;
            }
        }
        return null;
    }*/
}
