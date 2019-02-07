package com.simple.domain_model.domain;

public enum DataType {
    String,
    DateTime,
    ChoiceValue,
    Long
//    String("java.lang.String"),
//    DateTime("java.sql.Timestamp"),
//    ChoiceValue("com.simple.domain_model.domain.ChoiceValue"),
//    Long("java.lang.Long");

/*    private final String propertyType;

    DataType(String className) {
        this.propertyType = className;
    }

    public java.lang.String getPropertyType() {
        return propertyType;
    }

    public static DataType getValue(String className) {
        for (DataType type : DataType.values()) {
            if (type.getPropertyType().equalsIgnoreCase(className)) {
                return type;
            }
        }
        return null;
    }*/
}
