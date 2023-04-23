//package pl.qus.maxvector.hibernate.customtypes;
//
//import org.hibernate.boot.SessionFactoryBuilder;
//import org.hibernate.boot.spi.MetadataImplementor;
//import org.hibernate.boot.spi.SessionFactoryBuilderFactory;
//import org.hibernate.boot.spi.SessionFactoryBuilderImplementor;
//import org.slf4j.LoggerFactory;
//
//public class CustomDataTypesRegistration implements SessionFactoryBuilderFactory {
//
//    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CustomDataTypesRegistration.class);
//
//    @Override
//    public SessionFactoryBuilder getSessionFactoryBuilder(final MetadataImplementor metadata, final SessionFactoryBuilderImplementor defaultBuilder) {
//        logger.info("Registering Postgres vector custom type in hibernate");
//        metadata.getTypeResolver().registerTypeOverride(PostgresVectorDatatype.INSTANCE);
//        return defaultBuilder;
//    }
//}