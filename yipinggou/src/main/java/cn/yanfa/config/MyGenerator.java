package cn.yanfa.config;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

import static java.util.UUID.randomUUID;

/**
 * 自定义uuid，主键使用此对象时，不赋值时使用save方法直接insert，当赋值后使用save会先select然后再insert或update
 */
public class MyGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return randomUUID().toString().replace("-", "");
    }
}
