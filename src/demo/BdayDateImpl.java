package demo;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.Date;

public class BdayDateImpl implements BdayDate {
	
	private ZonedDateTime date;
	private ZoneId friendZoneId;
	private ZoneId yourCurrentZoneId;
	private final ZoneId UTC_ZONEID = ZoneId.of("UTC");
	private String email;
	private String friendName;
	private String topic;
	
	public BdayDateImpl(ZonedDateTime date, ZoneId friendZoneId, ZoneId yourCurrentZoneId, String email, String friendName, String topic) {
		this.date = date;
		this.friendZoneId = friendZoneId;
		this.yourCurrentZoneId = yourCurrentZoneId;
		this.email = email;
		this.friendName = friendName;
		this.topic = topic;
	}
	

	public ZoneId getFriendZoneId() {
		return friendZoneId;
	}


	public void setFriendZoneId(ZoneId friendZoneId) {
		this.friendZoneId = friendZoneId;
	}


	public ZoneId getYourCurrentZoneId() {
		return yourCurrentZoneId;
	}


	public void setYourCurrentZoneId(ZoneId yourCurrentZoneId) {
		this.yourCurrentZoneId = yourCurrentZoneId;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getFriendName() {
		return friendName;
	}


	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}


	public String getTopic() {
		return topic;
	}


	public void setTopic(String topic) {
		this.topic = topic;
	}


	public ZoneId getUTC_ZONEID() {
		return UTC_ZONEID;
	}


	@Override
	public String toString() {
		return "BdayDateImpl [date=" + date + ", friendZoneId=" + friendZoneId + ", yourCurrentZoneId="
				+ yourCurrentZoneId + ", UTC_ZONEID=" + UTC_ZONEID + ", email=" + email + ", friendName=" + friendName
				+ ", topic=" + topic + "]";
	}


	public void setDate(ZonedDateTime date) {
		this.date = date;
	}


	@Override
	public ZonedDateTime getDate() {
		// TODO Auto-generated method stub
		return date;
	}


	@Override
	public int compareTo(BdayDateImpl o) {
		// TODO Auto-generated method stub
		return date.compareTo(o.getDate());
	}


}
