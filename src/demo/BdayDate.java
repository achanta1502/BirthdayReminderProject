package demo;

import java.time.ZoneId;

public interface BdayDate<T> extends Comparable<T>{
	
	 public T getDate();
	 
	 public void SetDate(T date);
	 
	 public T getUTCTime();
	 
	 public ZoneId getZoneId();
}
