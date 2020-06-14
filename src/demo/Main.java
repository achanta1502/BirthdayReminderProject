package demo;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.amazonaws.services.dynamodbv2.document.Item;

public class Main {

	public static void main(String[] args) {
		String dynamoEndPoint = args[0];
		String snsEndPoint = args[1];
		String region = args[2];
		int PORT = Integer.parseInt(args[3]);
		String access_key = args[4];
		String secret_key = args[5];
		String stage = "PROD";
		Server myserver = new Server(PORT);
		DynamoDb opers = null;
		Publish publish = null;
		if(stage == "PROD") {
		System.out.println("enter production");
		opers = new DynamoDbOper(dynamoEndPoint, region, access_key, secret_key);
		publish = new PublishMessage(access_key, secret_key, snsEndPoint, region);
		} else {
		opers = new DummyDynamoDb();
		publish = new DummyPublish();
		}
		Builder.dynamoDB(opers);
		Builder.sns(publish);
		ExecutorService executor = Executors.newFixedThreadPool(3);
		try {
			myserver.startServer(executor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		InMemoryQueue inMem = InMemoryQueue.init(100);
		
		
	}

}
