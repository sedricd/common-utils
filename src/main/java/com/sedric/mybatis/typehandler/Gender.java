package com.sedric.mybatis.typehandler;

public enum Gender implements BooleanEnum {
    MAN(true),
    /** */
    WOMAN(false), ;

    private Boolean value;

    private Gender(Boolean isMan) {
        this.value = isMan;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

}
