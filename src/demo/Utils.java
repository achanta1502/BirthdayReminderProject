package demo;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Utils {
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mma z");
	public Date convertToUTC() {
		return new Date();
	}
	public static void print() {
		LocalDateTime time = LocalDateTime.now();
		System.out.println(time.toString());
		//System.out.println(formatter.format(time));
		ZonedDateTime foreign = time.atZone(ZoneId.of("UTC"));
		System.out.println(formatter.format(foreign));
		ZonedDateTime utc = foreign.withZoneSameInstant(ZoneId.of("Asia/Kolkata"));
		System.out.println(formatter.format(utc));
		System.out.println("a".compareTo("b"));
		Set<String> set = ZoneId.getAvailableZoneIds();
		int j = 0;
		Queue<ZonedDateTime> queue = new PriorityQueue<ZonedDateTime>();
		queue.add(utc);
		queue.add(foreign);
		for(String i: set) {
			queue.add(utc.withZoneSameInstant(ZoneId.of(i)));
			
			if(j >= 10) break;
			
			j++;
		}
		System.out.println(queue);
		while(!queue.isEmpty()) {
			System.out.println(queue.remove());
		}
		LocalDateTime e1 = LocalDateTime.parse("06/08/2020 03:09AM UTC", formatter);
		ZonedDateTime e2 = ZonedDateTime.of(e1, ZoneId.of("UTC"));
		System.out.println(formatter.format(e2));
	}
	
	public static ZonedDateTime convertToZonedDate(String formatTime) {
		
		LocalDateTime e1 = LocalDateTime.parse(formatTime, formatter);
		return ZonedDateTime.of(e1, ZoneId.of("UTC")); 
	}
}
