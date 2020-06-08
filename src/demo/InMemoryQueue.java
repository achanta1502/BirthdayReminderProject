package demo;


import java.util.concurrent.PriorityBlockingQueue;

public class InMemoryQueue {
	private final PriorityBlockingQueue<BdayDate> queue;
	private static InMemoryQueue inMem = null;
	private int lock = 0;
	
	public InMemoryQueue(int capapcity) {
		queue = new PriorityBlockingQueue<BdayDate>(capapcity);
	}
	
	public static InMemoryQueue init(int capacity) {
		if(inMem == null) {
		inMem = new InMemoryQueue(capacity);
		}
		return inMem;
	}
	
	public void addToInMemory(BdayDate time) {
		queue.add(time);
	}
	
	public void removeFromInMemory(BdayDate time) {
		queue.remove(time);
	}
	
	public BdayDate peekInMemory() {
		return queue.peek();
	}
	
	public void setLock() {
		lock = 1;
	}
	
	public void releaseLock() {
		lock = 0;
	}
	
	public boolean isLock() {
		return lock == 1;
	}
}
