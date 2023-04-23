//package pl.qus.maxvector.hibernate.customtypes;
//
//import org.hibernate.type.descriptor.WrapperOptions;
//import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
//import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;
//import org.hibernate.type.descriptor.spi.JdbcRecommendedSqlTypeMappingContext;
//import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
//import pl.qus.maxvector.model.EmbVector;
//
//
//public class PostgresVectorDescriptor extends AbstractTypeDescriptor<EmbVector> {
//
//    public static final PostgresVectorDescriptor INSTANCE =
//            new PostgresVectorDescriptor();
//
//    public PostgresVectorDescriptor() {
//        super(EmbVector.class, ImmutableMutabilityPlan.INSTANCE);
//    }
//
//    @Override
//    public String toString(EmbVector value) {
//        return "'"+value.toString()+"'";
//    }
//
//    @Override
//    public EmbVector fromString(String string) {
//        return EmbVector.from(string);
//    }
//
//    // unwrap() is called during PreparedStatement binding to convert LocalDate to a String type, which is mapped to VARCHAR.
//    // nasz wektor na SQL
//    @Override
//    public <X> X unwrap(EmbVector value, Class<X> type, WrapperOptions options) {
//
//        if (value == null)
//            return null;
//
////        return (X)("Kupsko"+type.getCanonicalName());
//
//        if (String.class.isAssignableFrom(type))
//            return (X) toString(value);
//
//        throw new IllegalArgumentException("Byłem w unwrap");
//
////        throw unknownUnwrap(type);
//    }
//
//    // Likewise, wrap() is called during ResultSet retrieval to convert String to a Java LocalDate.
//    // sql na nasz wektor
//    @Override
//    public <X> EmbVector wrap(X value, WrapperOptions options) {
//        if (value == null)
//            return null;
//
//        if(value instanceof String)
//            return EmbVector.from((String) value);
//
//        throw new IllegalArgumentException("Byłem we wrap");
//
//        //throw unknownWrap(value.getClass());
//
//    }
//
//    @Override
//    public SqlTypeDescriptor getJdbcRecommendedSqlType(JdbcRecommendedSqlTypeMappingContext context) {
//        return super.getJdbcRecommendedSqlType(context);
//    }
//}