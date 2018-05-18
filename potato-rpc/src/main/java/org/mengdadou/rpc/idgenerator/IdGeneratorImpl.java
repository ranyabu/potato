package org.mengdadou.rpc.idgenerator;

/**
 * Created by mengdadou on 17-9-27.
 */
public class IdGeneratorImpl implements IdGenerator {
	
	public long nextId() {
		return IdWorker.instance().nextId();
	}
}
