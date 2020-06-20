package demo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.amazonaws.services.dynamodbv2.document.Item;

public class LoadingFriendsData implements Runnable{
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
	private InMemoryQueue queue;
	private AtomicBoolean quitter;
	
	public LoadingFriendsData(InMemoryQueue queue, AtomicBoolean quitter) {
		this.queue = queue;
		this.quitter = quitter;
	}
	
	@Override
	public void run() {
		while(quitter.get()) {
			try {
				
				LocalDateTime time = LocalDateTime.now();
				LocalDateTime after = time.plusDays(1);
				LocalDateTime before = time.minusDays(1);
				Iterator<Item> listBirthdays = Builder.db().readBirthdayData("BirthDayTable", formatter.format(before), formatter.format(after));
				List<BdayDate> friendBdayDates = getData(listBirthdays);
				
				ExecutorService executor = Executors.newSingleThreadExecutor();
				executor.execute(new LoadInMemoryQueue(friendBdayDates, queue, quitter));
				executor.shutdown();
				
				Thread.sleep(15 * 60 * 1000);
				
			} catch (Exception e) {
				EventData.info(e.getMessage());
				e.printStackTrace();
				quitter.set(false);
			}	
		}
		
	}
	
	private List<BdayDate> getData(Iterator<Item> listDays) {
		List<BdayDate> friendsList = new ArrayList<>();
		while(listDays.hasNext()) {
			Item item = listDays.next();
			BdayDate date = new BdayDateImpl(
					getDateTime(item.get("BirthDay").toString(), item.get("FriendTimeZone").toString()),
					ZoneId.of(item.get("FriendTimeZone").toString()),
					ZoneId.of(item.get("MyTimeZone").toString()),
					item.get("Email").toString(),
					item.get("FriendName").toString(),
					item.get("Topic").toString());
			friendsList.add(date);
		}
		
		
		
		return friendsList;
	}
	
	private ZonedDateTime getDateTime(String date, String zone) {
		String[] args = date.split("-");
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime convertedDate = LocalDateTime.of(now.getYear(), Month.of(Integer.parseInt(args[0])), Integer.parseInt(args[1]), 0, 0);
		 
		ZonedDateTime conv = convertedDate.atZone(ZoneId.of(zone));
		return conv.withZoneSameInstant(ZoneId.of("UTC"));
	}

}
