package pl.qus.maxvector.hibernate.customtypes;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;
import java.util.List;

import static java.util.Arrays.asList;

// https://medium.com/@diee.roman.22/using-custom-hibernate-types-in-native-queries-364f9120b79a
// to rzekomo umożliwia automatyczne mapowanie do wektora, zamiast castu ze stringa
public class PostgresVectorType implements UserType {
    //  sqlTypes() : Tells Hibernate the SQL type we are mapping. In this case, I am using java.sql.Types.ARRAY that is actually the type with which the driver is gonna identify the field roles.
    @Override
    public int[] sqlTypes() {
        return new int[] {Types.ARRAY};
    }


    // The resulting java class to map to.
    @Override
    public Class returnedClass() {
        return List.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return false;
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return 0;
    }

    // This method is the one in charge of creating the List object from the incoming SQL array value.
    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
            throws HibernateException, SQLException {
        Array array = rs.getArray(names[0]);
        if (array != null) {
            if (array.getBaseType() == Types.VARCHAR) {
                return asList((String[])array.getArray());
            }
        }
        return null;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if (value != null && st != null) {
            Array array = session.connection().createArrayOf("text", ((List)value).toArray());
            st.setArray(index, array);
        } else {
            st.setNull(index, sqlTypes()[0]);
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return null;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return null;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return null;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return null;
    }
}
