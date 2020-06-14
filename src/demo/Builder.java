package demo;

import com.amazonaws.services.sns.AmazonSNS;

public class Builder {
	private static DynamoDb dynamoDB;
	private static Publish snsClient;
	
	public static void dynamoDB(DynamoDb db) {
		dynamoDB = db;
	}
	
	public static DynamoDb db() {
		return dynamoDB;
	}
	
	public static void sns(Publish sns) {
		snsClient = sns;
	}
	
	public static Publish sns() {
		return snsClient;
	}
}
