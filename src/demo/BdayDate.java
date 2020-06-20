package demo;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public interface BdayDate extends Comparable<BdayDateImpl>{
	
	 public ZonedDateTime getDate();
	 
	 public void setDate(ZonedDateTime date);
	 
		public ZoneId getFriendZoneId();


		public void setFriendZoneId(ZoneId friendZoneId);


		public ZoneId getYourCurrentZoneId();


		public void setYourCurrentZoneId(ZoneId yourCurrentZoneId);


		public String getEmail();


		public void setEmail(String email);


		public String getFriendName();


		public void setFriendName(String friendName);


		public String getTopic();


		public void setTopic(String topic);


		public ZoneId getUTC_ZONEID();

		int compareTo(BdayDateImpl o);
		

}
