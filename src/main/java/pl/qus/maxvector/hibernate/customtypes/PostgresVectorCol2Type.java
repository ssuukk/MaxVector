package pl.qus.maxvector.hibernate.customtypes;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import pl.qus.maxvector.model.EmbVector;

import java.io.Serializable;
import java.sql.*;

// https://medium.com/@diee.roman.22/using-custom-hibernate-types-in-native-queries-364f9120b79a
// to rzekomo umożliwia automatyczne mapowanie do wektora, zamiast castu ze stringa

// podobno odpala się to tak w entity:
// @Type(type = "pl.qus.maxvector.hibernate.customtypes.PostgresVectorCol2Type
// private EmbVector vector;

// a tu chyba najlepszy opis:
// https://thorben-janssen.com/persist-postgresqls-jsonb-data-type-hibernate/

public class PostgresVectorCol2Type implements UserType {
    //  sqlTypes() : Tells Hibernate the SQL type we are mapping. In this case, I am using java.sql.Types.ARRAY that is actually the type with which the driver is gonna identify the field roles.
    //  The sqlTypes() method returns an array of SQL types that the custom type maps to.
    // Tablica, bo wartość docelowa może być przedstawiana x kolumnach

    public static final PostgresVectorCol2Type INSTANCE = new PostgresVectorCol2Type();

    @Override
    public int[] sqlTypes() {
        return new int[] { Types.OTHER };
    }

    // The resulting java class to map to.
    // The returnedClass() method returns the Java type that the custom type maps to.
    @Override
    public Class returnedClass() {
        return EmbVector.class;
    }

    // This method is the one in charge of creating the List object from the incoming SQL array value.
    // The nullSafeGet() and nullSafeSet() methods handle the mapping between the Java type and the SQL type.
    //  nullSafeGet() and nullSafeSet(), where you need to implement the logic how you are going to get the values from database and save the values into database respectively and rest of the methods are be self-explanatory.

    // zamiana z SQL na "nasz"
    // w rs mamy Vector zwrócić EmbVector
    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
            throws HibernateException, SQLException {

        int columnType = rs.getMetaData().getColumnType(2); // 1111 czyli other

        if(columnType == Types.OTHER) {
            String wektor = rs.getString(2);

            return EmbVector.from(wektor);
        }
        else
            return null;
    }

    // zamiana z naszego na SQL
    // w value mamy EmbVector, do rs zwrócić Vector
    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
            throws HibernateException, SQLException {

        if (value != null && st != null) {
            st.setObject(2, value, Types.OTHER);
        } else {
            st.setNull(index, Types.OTHER);
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        Object deepCopy = deepCopy(value);
        if (!(deepCopy instanceof Serializable)) {
            return (Serializable) deepCopy;
        }
        return null;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return deepCopy(original);
    }


    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return false;
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x != null ? x.hashCode() : 0;
    }


}
