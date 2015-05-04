package com.sedric.mybatis.typehandler;

public class BooleanEnumValueReflect {

    /**
     * 获取boolean枚举的所有枚举类型
     * 
     * @param classType
     * @return
     */
    public static <T extends Enum<T>> T[] getEnumTypes(Class<T> classType) {
        return classType.getEnumConstants();
    }

    /**
     * 获取boolean枚举的所有枚举值
     * 
     * @param classType
     * @return
     */
    public static <T extends Enum<T> & BooleanEnum> Boolean[] getEnumValues(Class<T> classType) {

        T[] enums = classType.getEnumConstants();
        Boolean[] values = new Boolean[enums.length];
        for (int i = 0; i < enums.length; i++) {
            values[i] = enums[i].getValue();
        }

        return values;
    }

    public static <T extends Enum<T> & BooleanEnum> T getEnumTypeFromValue(Class<T> classType, Boolean value) {
        T[] enums = classType.getEnumConstants();
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].getValue().equals(value)) {
                return enums[i];
            }
        }
        return null;
    }
}
