package demo;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoadInMemoryQueue implements Runnable{
	
	private InMemoryQueue inMemQueue = null;
	private List<BdayDate> friendBdayDates = null;
	private AtomicBoolean quitter;
	
	public LoadInMemoryQueue(List<BdayDate> friendBdayDates, InMemoryQueue inMemQueue, AtomicBoolean quitter) {
		this.inMemQueue = inMemQueue;
		this.friendBdayDates = friendBdayDates;
		this.quitter = quitter;
	}
	
	@Override
	public void run(){
		try {
		loadingQueue(friendBdayDates);	
		System.out.println(inMemQueue.toString());
		} catch (Exception e) {
			EventData.info("Unknown exception: " + e.getMessage());
			e.printStackTrace();
			quitter.set(false);
		}
	}
	
	private void loadingQueue(List<BdayDate>  friendBdaydates) throws Exception{
		for(BdayDate temp: friendBdayDates) {
			if(!inMemQueue.contains(temp))
			inMemQueue.addToInMemory(temp);
		}
	}
}
