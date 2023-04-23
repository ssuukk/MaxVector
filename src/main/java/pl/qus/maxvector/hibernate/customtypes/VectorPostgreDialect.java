package pl.qus.maxvector.hibernate.customtypes;

import org.hibernate.dialect.PostgreSQL10Dialect;
import org.hibernate.dialect.PostgreSQL94Dialect;
import pl.qus.maxvector.model.EmbVector;

import java.sql.Types;

public class VectorPostgreDialect extends PostgreSQL10Dialect {

    public VectorPostgreDialect() {
        //this.registerColumnType(Types.OTHER, "vector");
        registerHibernateType(Types.OTHER, EmbVector.class.getName());
    }
}