package pl.qus.maxvector.hibernate.customtypes;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

public class EVectorDatatype
        extends AbstractSingleColumnStandardBasicType<EVector> {

    public static final EVectorDatatype INSTANCE = new EVectorDatatype();

    public EVectorDatatype() {
        // First, is an instance of SqlTypeDescriptor, which is Hibernate's SQL type representation, which is VARCHAR for our example.
        // And, the second argument is an instance of JavaTypeDescriptor which represents Java type.
        super(VarcharTypeDescriptor.INSTANCE, EVectorStringJavaDescriptor.INSTANCE);
    }

    @Override
    public String getName() {
        return "EVector";
    }
}