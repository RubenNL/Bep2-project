package nl.hu.bep2.vliegmaatschappij.domein;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;

public class UUIDGenerator implements IdentifierGenerator {
	public static final String generatorName = "uuidGenerator";

	@Override
	public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object object) throws HibernateException {
		return UUID.randomUUID().toString();
	}
}

