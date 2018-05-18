package org.mengdadou.rpc.idgenerator;


import org.mengdadou.net.util.SpiUtil;

/**
 * Created by mengdadou on 17-10-16.
 */
public enum IdGeneratorEnum {
	INST;
	private IdGenerator idGenerator;
	
	IdGeneratorEnum() {
		this.idGenerator = SpiUtil.getServiceImpl(IdGenerator.class);
	}
	
	public IdGenerator getIdGenerator() {
		return idGenerator;
	}
}
