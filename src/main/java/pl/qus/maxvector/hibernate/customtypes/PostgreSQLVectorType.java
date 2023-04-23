//package pl.qus.maxvector.hibernate.customtypes;
//
//import io.hypersistence.utils.hibernate.type.ImmutableType;
//import io.hypersistence.utils.hibernate.type.util.Configuration;
//import org.hibernate.HibernateException;
//import org.hibernate.MappingException;
//import org.hibernate.engine.jdbc.Size;
//import org.hibernate.engine.spi.Mapping;
//import org.hibernate.engine.spi.SessionFactoryImplementor;
//import org.hibernate.engine.spi.SharedSessionContractImplementor;
//import org.hibernate.type.Type;
//import pl.qus.maxvector.model.EmbVector;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class PostgreSQLVectorType extends ImmutableType<EmbVector> {
//
//    public static final PostgreSQLVectorType INSTANCE = new PostgreSQLVectorType();
//
//    public PostgreSQLVectorType() {
//        super(EmbVector.class);
//    }
//
//    public PostgreSQLVectorType(org.hibernate.type.spi.TypeBootstrapContext typeBootstrapContext) {
//        super(EmbVector.class, new Configuration(typeBootstrapContext.getConfigurationSettings()));
//    }
//
//    @Override
//    protected EmbVector get(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
//        return null;
//    }
//
//    @Override
//    protected void set(PreparedStatement st, EmbVector value, int index, SharedSessionContractImplementor session) throws SQLException {
//
//    }
//
//    @Override
//    public int[] sqlTypes(Mapping mapping) throws MappingException {
//        return new int[0];
//    }
//
//    @Override
//    public int[] sqlTypes() {
//        return new int[0];
//    }
//
//
//    @Override
//    public Size[] dictatedSizes(Mapping mapping) throws MappingException {
//        return new Size[0];
//    }
//
//    @Override
//    public Size[] defaultSizes(Mapping mapping) throws MappingException {
//        return new Size[0];
//    }
//
//    @Override
//    public Object nullSafeGet(ResultSet rs, String name, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
//        return null;
//    }
//
//    @Override
//    public Object hydrate(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
//        return null;
//    }
//
//    @Override
//    public Object resolve(Object value, SharedSessionContractImplementor session, Object owner) throws HibernateException {
//        return null;
//    }
//
//    @Override
//    public Object semiResolve(Object value, SharedSessionContractImplementor session, Object owner) throws HibernateException {
//        return null;
//    }
//
//    @Override
//    public Type getSemiResolvedType(SessionFactoryImplementor factory) {
//        return null;
//    }
//
//
//    @Override
//    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
//        return null;
//    }
//}
