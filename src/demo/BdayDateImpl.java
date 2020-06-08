package demo;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class BdayDateImpl<T extends ZonedDateTime> implements BdayDate<T>{
	
	private T date;
	private ZoneId friendZoneId;
	private ZoneId yourCurrentZoneId;
	private final ZoneId UTC_ZONEID = ZoneId.of("UTC");
	private String email;
	private String friendName;
	
	public BdayDateImpl(T date, ZoneId friendZoneId, ZoneId yourCurrentZoneId, String email, String friendName) {
		this.date = date;
		this.friendZoneId = friendZoneId;
		this.yourCurrentZoneId = yourCurrentZoneId;
		this.email = email;
		this.friendName = friendName;
	}
	

	@Override
	public int compareTo(T other) {
		// TODO Auto-generated method stub
		ZonedDateTime local = date.withZoneSameInstant(UTC_ZONEID);
		return date.compareTo(other);
	}

	@Override
	public T getDate() {
		// TODO Auto-generated method stub
		return date;
	}

	@Override
	public void SetDate(T date) {
		// TODO Auto-generated method stub
		this.date = date;
	}

	@Override
	public T getUTCTime() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public T convertToUTC(T date) {
		
		return null;
	}


	@Override
	public ZoneId getZoneId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
