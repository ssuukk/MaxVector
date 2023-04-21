package pl.qus.maxvector.hibernate.customtypes;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

public class PostgresVectorDatatype
        extends AbstractSingleColumnStandardBasicType<PostgresVector> {

    public static final PostgresVectorDatatype INSTANCE = new PostgresVectorDatatype();

    public PostgresVectorDatatype() {
        // First, is an instance of SqlTypeDescriptor, which is Hibernate's SQL type representation, which is VARCHAR for our example.
        // And, the second argument is an instance of JavaTypeDescriptor which represents Java type.
        super(VarcharTypeDescriptor.INSTANCE, PostgresVectorDescriptor.INSTANCE);
    }

    @Override
    public String getName() {
        return "EVector";
    }
}