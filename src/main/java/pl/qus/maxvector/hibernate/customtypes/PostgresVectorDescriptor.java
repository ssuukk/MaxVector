package pl.qus.maxvector.hibernate.customtypes;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;
import org.hibernate.type.descriptor.spi.JdbcRecommendedSqlTypeMappingContext;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
import pl.qus.maxvector.model.PostgresVector;


public class PostgresVectorDescriptor extends AbstractTypeDescriptor<PostgresVector> {

    public static final PostgresVectorDescriptor INSTANCE =
            new PostgresVectorDescriptor();

    public PostgresVectorDescriptor() {
        super(PostgresVector.class, ImmutableMutabilityPlan.INSTANCE);
    }

    @Override
    public String toString(PostgresVector value) {
        return "'"+value.toString()+"'";
    }

    @Override
    public PostgresVector fromString(String string) {
        return PostgresVector.from(string);
    }

    // unwrap() is called during PreparedStatement binding to convert LocalDate to a String type, which is mapped to VARCHAR.
    // nasz wektor na SQL
    @Override
    public <X> X unwrap(PostgresVector value, Class<X> type, WrapperOptions options) {

        if (value == null)
            return null;

//        return (X)("Kupsko"+type.getCanonicalName());

        if (String.class.isAssignableFrom(type))
            return (X) toString(value);

        throw unknownUnwrap(type);
    }

    // Likewise, wrap() is called during ResultSet retrieval to convert String to a Java LocalDate.
    // sql na nasz wektor
    @Override
    public <X> PostgresVector wrap(X value, WrapperOptions options) {
        if (value == null)
            return null;

        if(value instanceof String)
            return PostgresVector.from((String) value);

        throw unknownWrap(value.getClass());

    }

    @Override
    public SqlTypeDescriptor getJdbcRecommendedSqlType(JdbcRecommendedSqlTypeMappingContext context) {
        return super.getJdbcRecommendedSqlType(context);
    }
}