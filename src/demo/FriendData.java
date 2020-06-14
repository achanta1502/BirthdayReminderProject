package demo;

public class FriendData {
	private String name;
	private String email;
	private String date;
	private String friendTimeZone;
	private String myTimeZone;
	private String topicArn;
	
	public String getTopicArn() {
		return topicArn;
	}

	public void setTopicArn(String topicArn) {
		this.topicArn = topicArn;
	}

	public FriendData() {
		super();
	}
	
	public FriendData(String name, String email, String date, String friendTimeZone, String myTimeZone) {
		super();
		this.name = name;
		this.email = email;
		this.date = date;
		this.friendTimeZone = friendTimeZone;
		this.myTimeZone = myTimeZone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public String getFriendTimeZone() {
		return friendTimeZone;
	}

	public void setFriendTimeZone(String friendTimeZone) {
		this.friendTimeZone = friendTimeZone;
	}

	public String getMyTimeZone() {
		return myTimeZone;
	}

	public void setMyTimeZone(String myTimeZone) {
		this.myTimeZone = myTimeZone;
	}

	@Override
	public String toString() {
		return "FriendData [name=" + name + ", email=" + email + ", date=" + date + ", friendTimeZone=" + friendTimeZone
				+ ", myTimeZone=" + myTimeZone + ", topicArn=" + topicArn + "]";
	}

}
