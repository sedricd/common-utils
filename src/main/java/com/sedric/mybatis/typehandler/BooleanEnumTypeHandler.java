package com.sedric.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * mybatis3.1版本以后，typehandler已经无需在mybatis config.properties文件中注册，可以直接在mapper.xml中使用，具体如下：
 * 
 * resultMap中的使用： <result column="gender" property="gender" jdbcType="CHAR"
 * typeHandler="com.andrew.mybatis.typehandler.BooleanEnumTypeHandler" />
 * 
 * insert或者update中的使用：
 * #{gender,jdbcType=CHAR,typeHandler=com.andrew.mybatis.typehandler.BooleanEnumTypeHandler,javaType=
 * com.qisa.oms.enums.Gender},
 * 
 * 其中resultMap的result属性中可以不用指定javaType，在构造resultMap时，mybatis会根据resultMap的javaType和result的property属性解析出应有的javaType。
 * 
 * 但是在insert或update或者动态查询条件中，必须指定javaType
 * 
 * @author huyuehan
 *
 * @param <T>
 */
public class BooleanEnumTypeHandler<T extends Enum<T> & BooleanEnum> implements TypeHandler<T> {

    private Class<T> classType;

    public BooleanEnumTypeHandler(Class<T> classType) {
        if (null == classType) {
            throw new RuntimeException("BooleanEnumTypeHandler constructor classType argument can not be null!");
        }
        this.classType = classType;
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        Object value = null;
        if (null == parameter) {
            ps.setNull(i, Types.INTEGER);
        }
        if (null == jdbcType) {
            ps.setString(i, "1");
        }
        else {
            if (parameter.getValue()) {
                if (jdbcType.equals(JdbcType.CHAR) || jdbcType.equals(JdbcType.VARCHAR)) {
                    value = "1";
                }
                else if (jdbcType.equals(JdbcType.TINYINT) || jdbcType.equals(JdbcType.SMALLINT)) {
                    value = 1;
                }
                else if (jdbcType.equals(JdbcType.BOOLEAN)) {
                    value = true;
                }
                else {
                    value = "1";
                }
            }
            else {
                if (jdbcType.equals(JdbcType.VARCHAR)) {
                    value = "0";
                }
                else if (jdbcType.equals(JdbcType.CHAR)) {
                    value = '0';
                }
                else if (jdbcType.equals(JdbcType.TINYINT) || jdbcType.equals(JdbcType.SMALLINT)) {
                    value = 0;
                }
                else if (jdbcType.equals(JdbcType.BOOLEAN)) {
                    value = false;
                }
                else {
                    value = "0";
                }
            }
            ps.setObject(i, value, jdbcType.TYPE_CODE);
        }
    }

    @Override
    public T getResult(ResultSet rs, String columnName) throws SQLException {
        Object value = rs.getObject(columnName);

        boolean booleanValue = false;

        if (null == value) {
            return null;
        }
        if ("1".equals(value) || new Integer(1).equals(value)) {
            booleanValue = true;
        }
        return BooleanEnumValueReflect.getEnumTypeFromValue(classType, booleanValue);
    }

    @Override
    public T getResult(ResultSet rs, int columnIndex) throws SQLException {
        Object value = rs.getObject(columnIndex);
        boolean booleanValue = false;
        if (null == value) {
            return null;
        }
        if ("1".equals(value) || new Integer(1).equals(value)) {
            booleanValue = true;
        }
        return BooleanEnumValueReflect.getEnumTypeFromValue(classType, booleanValue);
    }

    @Override
    public T getResult(CallableStatement cs, int columnIndex) throws SQLException {
        Object value = cs.getObject(columnIndex);
        boolean booleanValue = false;
        if (null == value) {
            return null;
        }
        if ("1".equals(value) || new Integer(1).equals(value)) {
            booleanValue = true;
        }
        return BooleanEnumValueReflect.getEnumTypeFromValue(classType, booleanValue);
    }

    public static void main(String[] args) {

        System.out.println(BooleanEnumValueReflect.getEnumTypeFromValue(Gender.class, true));
    }
}
