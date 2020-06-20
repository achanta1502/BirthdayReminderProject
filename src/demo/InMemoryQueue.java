package demo;


import java.time.ZonedDateTime;
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
		if(!queue.contains(time))
		queue.add(time);
	}
	
	public void removeFromInMemory(BdayDate time) {
		queue.remove(time);
	}
	
	public BdayDate pop() {
		return queue.poll();
	}
	
	public boolean isEmpty() {
		return queue.isEmpty();
	}
	
	public BdayDate peekInMemory() {
		return queue.peek();
	}
	
	public boolean contains(BdayDate temp) {
		return queue.contains(temp);
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

	@Override
	public String toString() {
		return "InMemoryQueue [queue=" + queue + "]";
	}
	
	
}
