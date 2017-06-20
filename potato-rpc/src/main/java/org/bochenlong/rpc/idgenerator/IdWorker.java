package org.bochenlong.rpc.idgenerator;

import org.bochenlong.rpc.RpcManager;

import java.util.function.LongSupplier;
import java.util.function.LongUnaryOperator;

/**
 * Created by bochenlong on 17-1-3.
 */
public class IdWorker {
    private static class Holder {
        private static IdWorker idWorker = new IdWorker(
                RpcManager.singleton().getID_WORKER_SEQ1(), RpcManager.singleton().getID_WORKER_SEQ2());
    }
    
    public static IdWorker instance() {
        return Holder.idWorker;
    }
    
    public IdWorker(long workerId1, long workerId2) {
        if (workerId1 > maxWorkerId1 || workerId1 < 0) {
            throw new IllegalArgumentException(String.format("workerId1 can't be greater than %d or less than 0", maxWorkerId1));
        }
        
        if (workerId2 > maxWorkerId2 || workerId2 < 0) {
            throw new IllegalArgumentException(String.format("workerId2 Id can't be greater than %d or less than 0", maxWorkerId2));
        }
        
        this.workerId1 = workerId1;
        this.workerId2 = workerId2;
    }
    
    private long workerId1;//
    private long workerId2;//
    
    @SuppressWarnings("FieldCanBeLocal")
    private final long twEpoch = 1288834974657L;
    private final long workerId1Bits = 5L;
    private final long workerId2Bits = 5L;
    @SuppressWarnings("FieldCanBeLocal")
    private final long maxWorkerId1 = ~(-1L << workerId1Bits);
    @SuppressWarnings("FieldCanBeLocal")
    private final long maxWorkerId2 = ~(-1L << workerId2Bits);
    private final long sequenceBits = 12L;
    
    @SuppressWarnings("FieldCanBeLocal")
    private final long workerId1Shift = sequenceBits;
    @SuppressWarnings("FieldCanBeLocal")
    private final long workerId2Shift = sequenceBits + workerId1Bits;
    @SuppressWarnings("FieldCanBeLocal")
    private final long timestampLeftShift = sequenceBits + workerId1Bits + workerId2Bits;
    @SuppressWarnings({"FieldCanBeLocal"})
    private final long sequenceMask = ~(-1L << sequenceBits);
    
    private volatile long lastTimestamp = -1L;
    private volatile long sequence = 0L;
    
    private LongSupplier timeGen = System::currentTimeMillis;
    private LongUnaryOperator tilNextMillis = lastTimestamp -> {
        long timestamp = timeGen.getAsLong();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen.getAsLong();
        }
        return timestamp;
    };
    
    public synchronized long nextId() {
        long timestamp = timeGen.getAsLong();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", (lastTimestamp - timestamp)));
        }
        
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis.applyAsLong(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        return ((timestamp - twEpoch) << timestampLeftShift) |
                (workerId1 << workerId2Shift) |
                (workerId2 << workerId1Shift) |
                sequence;
    }
    
}
