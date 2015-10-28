package br.com.xlearning.enumeracao;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.apache.commons.lang.ObjectUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.IntegerType;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

/**
 * 
 * @author jesiel
 *
 * @param <E>
 */
public class GenericEnumPersistenceType<E> implements UserType, ParameterizedType {
    public static final String DEFAULT_CODE_VALUE_METHOD_NAME = "getChave";
    public static final String DEFAULT_FROM_VALUE_METHOD_NAME = "get";
    private Method codeValueMethod;
    private Method fromValueMethod;
    private Class<?> codeValueType;
    private Class<E> enumClass; 

    public GenericEnumPersistenceType(){}

    @Override
    @SuppressWarnings("unchecked")
    public void setParameterValues(Properties parameters) {
        String enumClassName = parameters.getProperty("enumClass");
        try {
            enumClass =  (Class<E>) Class.forName(enumClassName).asSubclass(Enum.class);
        } catch (ClassNotFoundException e) {
            throw new HibernateException("Enum class not found", e);
        }
        String identifierMethodName = parameters.getProperty("codeValueMethod", DEFAULT_CODE_VALUE_METHOD_NAME);
        try {
            codeValueMethod = enumClass.getMethod(identifierMethodName, new Class[0]);
            codeValueType = codeValueMethod.getReturnType();
        } catch (Exception e) {
            throw new HibernateException("Failed to obtain codeValue method. Setting default method name", e);
        }
        String valueOfMethodName = parameters.getProperty("fromValueMethod", DEFAULT_FROM_VALUE_METHOD_NAME);
        try {
            fromValueMethod = enumClass.getMethod(valueOfMethodName, new Class[] { codeValueType });
        } catch (Exception e) {
            throw new HibernateException("Failed to obtain fromValue method. Setting default method name", e);
        }
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        try {
            String propertyValue = resultSet.getString(names[0]);
            Integer propertyNumericValue;
            try {
                propertyNumericValue = Integer.valueOf(propertyValue);
            } catch (NumberFormatException e) {
                return null;
            }
            return fromValueMethod.invoke(enumClass, propertyNumericValue); 
        } catch (Exception e) {
            e.printStackTrace();
            throw new HibernateException("Exception while invoking valueOf method '" + fromValueMethod.getName() + "' of " + "enumeration class '" + enumClass + "'", e);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        try {
            if (null == value) {
                preparedStatement.setNull(index, Types.INTEGER);
            } else {
                Object identifier = codeValueMethod.invoke(value, new Object[0]);
                preparedStatement.setInt(index, (Integer) identifier);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new HibernateException("Exception while invoking identifier method '" + codeValueMethod.getName() + "' of " + "enumeration class '" + enumClass + "'", e);
        }

    }

    @Override
    public Class<E> returnedClass() {
        return enumClass;
    }

    @Override
    public int[] sqlTypes() {
         return new int[] { IntegerType.INSTANCE.sqlType() };
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return ObjectUtils.equals(x, y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        assert (x != null);
        return x.hashCode();
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

}