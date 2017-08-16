package org.mengdadou.rpc.exchange.assist;

import org.mengdadou.rpc.exchange.Response;
import org.mengdadou.rpc.exchange.ResponseCode;

import java.util.concurrent.*;

/**
 * Created by mengdadou on 16-11-14.
 */
public class PotatoFuture implements Future {
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    
    private volatile int status = RUNNING;
    
    private static final int RUNNING   = 1;
    private static final int CANCELLED = 2;
    private static final int COMPLETED = 10;
    private static final int ERROR     = 50;
    private static final int EXCEPTION = 51;
    
    private volatile Response result;
    
    public PotatoFuture() {
    }
    
    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return true;
    }
    
    @Override
    public boolean isCancelled() {
        return status == CANCELLED;
    }
    
    @Override
    public boolean isDone() {
        return status == COMPLETED || status == ERROR || status == EXCEPTION;
    }
    
    public boolean isRunning() {
        return status == RUNNING;
    }
    
    @Override
    public Response get() throws InterruptedException, ExecutionException {
        countDownLatch.await();
        return result();
    }
    
    @Override
    public Response get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (countDownLatch.await(timeout, unit)) {
            return result();
        }
        throw new TimeoutException("future get the result timeout");
    }
    
    public void set(Response v) {
        result = v;
        changeStatus(v);
    }
    
    public void cancel() {
        changeStatus(CANCELLED);
    }
    
    public int getStatus() {
        return status;
    }
    
    
    private Response result() throws ExecutionException, InterruptedException {
        switch (status) {
            case COMPLETED:
                return this.result;
            case ERROR:
                throw new ExecutionException(new Throwable(result.getData().toString()));
            case EXCEPTION:
                throw new ExecutionException(new Throwable(result.getData().toString()));
            case CANCELLED:
                throw new InterruptedException("future have be interrupted");
            default:
                throw new ExecutionException(new Throwable("unKnow exception"));
        }
    }
    
    private void changeStatus(Response v) {
        if (v.getCode() == ResponseCode.SUCCESS.getValue()) {
            changeStatus(COMPLETED);
        } else if (v.getCode() == ResponseCode.ERROR.getValue()) {
            changeStatus(ERROR);
        } else if (v.getCode() == ResponseCode.EXCEPTION.getValue()) {
            changeStatus(EXCEPTION);
        }
    }
    
    private synchronized void changeStatus(int s) {
        if (status != RUNNING) {
            return;
        }
        status = s;
        countDownLatch.countDown();
    }
}

