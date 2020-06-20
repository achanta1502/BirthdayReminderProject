package demo;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

public class PublishToFriends implements Runnable{
	
	private InMemoryQueue queue;
	private Publish publish;
	private AtomicBoolean quitter;
	
	public PublishToFriends(InMemoryQueue queue, Publish publish, AtomicBoolean quitter) {
		this.queue = queue;
		this.publish = publish;
		this.quitter = quitter;
	}
	
	@Override
	public void run() {
		while(quitter.get()) {
			try {
			if(!queue.isEmpty()) {
				publishMessage();
			}
			Thread.sleep(10 * 1000);
			} catch (Exception e) {
				EventData.info(e.getMessage());
				e.printStackTrace();
				quitter.set(false);
			}
		}
	}
	
	private void publishMessage() {
		boolean exist = true;
		ZonedDateTime utcTime = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
		while(exist && !queue.isEmpty()) {
			BdayDate bdayDate = queue.peekInMemory();
			//sending message before 30 minutes
			ZonedDateTime friendDate = bdayDate.getDate().minusMinutes(30);
			if(utcTime.isEqual(friendDate) || friendDate.isBefore(utcTime)) {
				try {
					System.out.println("sending date");
				queue.removeFromInMemory(bdayDate);
				sendNotification(bdayDate);
				} catch (Exception e) {
					EventData.info(e.getMessage());
					queue.addToInMemory(bdayDate);
				}
				
			} else {
				exist = false;
			}
		}
	}
	
	private void sendNotification(BdayDate friendDate) throws Exception {
		System.out.println("publishing friends " + friendDate.getFriendName());
		System.out.println(publish.publishMsg(friendDate.getTopic(), "wish happy birthday to your friend " + friendDate.getFriendName()));
	}

}
