package demo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.amazonaws.services.dynamodbv2.document.Item;

public class Main {

	public static void main(String[] args) {
		String dynamoEndPoint = args[0];
		String snsEndPoint = args[1];
		String region = args[2];
		int PORT = Integer.parseInt(args[3]);
		String access_key = args[4];
		String secret_key = args[5];
		AtomicBoolean quitter = new AtomicBoolean();
		quitter.set(true);
		String stage = args[6].toString();
		DynamoDb opers = null;
		Publish publish = null;
		if(stage.equals("PROD")) {
		opers = new DynamoDbOper(dynamoEndPoint, region, access_key, secret_key);
		publish = new PublishMessage(access_key, secret_key, snsEndPoint, region);
		} else {
		opers = new DummyDynamoDb();
		publish = new DummyPublish();
		}
		Builder.dynamoDB(opers);
		Builder.sns(publish);
		ExecutorService executorForLoading = Executors.newFixedThreadPool(2);
		InMemoryQueue queue = InMemoryQueue.init(10);
		PublishToFriends publishToFriends = new PublishToFriends(queue, publish, quitter);
		System.out.println(ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")));
		try {
			executorForLoading.execute(new LoadingFriendsData(queue, quitter));
			executorForLoading.execute(publishToFriends);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		ExecutorService serverExecutor = Executors.newSingleThreadExecutor();
		
			try {
				serverExecutor.execute(new Server(PORT, Executors.newFixedThreadPool(3), quitter));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		executorForLoading.shutdown();
		serverExecutor.shutdown();
		
	}

}
