package demo;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LoadInMemoryQueue implements Runnable{
	
	private InMemoryQueue inMemQueue = null;
	private List<BdayDate> friendBdayDates = null;
	
	public LoadInMemoryQueue(List<BdayDate> friendBdayDates, InMemoryQueue inMemQueue) {
		this.inMemQueue = inMemQueue;
		this.friendBdayDates = friendBdayDates;
	}
	
	@Override
	public void run(){
		try {
			
		loadingQueue(friendBdayDates);
		
		} catch (Exception e) {
			EventData.info("Unknown exception: " + e.getMessage());
		}
	}
	
	private void loadingQueue(List<BdayDate>  friendBdaydates) throws Exception{
		for(BdayDate temp: friendBdayDates) {
			inMemQueue.addToInMemory(temp);
		}
	}
}
