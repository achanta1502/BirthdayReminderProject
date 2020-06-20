package demo;

import java.util.HashMap;
import java.util.Iterator;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.local.shared.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.local.shared.model.PutRequest;

public class DynamoDbOper implements DynamoDb{
	private DynamoDB client;
	public DynamoDbOper(String endpoint, String region, String accessKey, String secretKey) {
		AWSCredentials creds = new BasicAWSCredentials(accessKey, secretKey);
		AmazonDynamoDB db = AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
				.withCredentials(new AWSStaticCredentialsProvider(creds))
				.build();
		this.client = new DynamoDB(db);
	}
	
	public Iterator<Item> readBirthdayData(String tableName, String start, String end) throws Exception {
		Table table = client.getTable(tableName);
		System.out.print(table.toString());
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#date", "BirthDay");

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":start", new String(start));
        valueMap.put(":end", new String(end));
        
        ScanSpec spec = new ScanSpec().withProjectionExpression("#date, FriendName, FriendTimeZone, MyTimeZone, Email, Topic")
        		.withFilterExpression("#date between :start and :end")
        		.withNameMap(nameMap)
        		.withValueMap(valueMap);
        
        ItemCollection<ScanOutcome> items = null;
        Iterator<Item> iterator = null;
        
        try {
        	
        	items = table.scan(spec);
        	iterator = items.iterator();
        	
        } catch (Exception e) {
        	EventData.info("Unable to read movie");
        	EventData.info(e.getMessage());
        	throw(e);
        }
        
        return iterator;
	}
	
	public void insertData(String tableName, FriendData friendData) throws Exception {
		System.out.println("inserting date to dynamodb");
		Table table = client.getTable(tableName);
		Item item = new Item()
				.withPrimaryKey("FriendName", friendData.getName())
				.withString("BirthDay", friendData.getDate())
				.withString("FriendTimeZone", friendData.getFriendTimeZone())
				.withString("MyTimeZone", friendData.getMyTimeZone())
				.withString("Email", friendData.getEmail())
				.withString("Topic", friendData.getTopicArn());

		try {
			PutItemOutcome output = table.putItem(item);
			if(output == null) throw(new Exception("can't insert data into dynamoDb"));
		} catch (Exception e) {
        	EventData.info("Unable to read movie");
			EventData.info(e.getMessage());
			throw(e);
		}
	}
	
	public Iterator<Item> ListFriendData(String tableName, String name) throws Exception {
		Table table = client.getTable(tableName);
		System.out.print(table.toString());
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#name", "FriendName");

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":name", new String(name));
        
        ScanSpec spec = new ScanSpec().withProjectionExpression("FriendName, BirthDay, FriendTimeZone, MyTimeZone, Email, Topic")
        		.withFilterExpression("#name = :name")
        		.withNameMap(nameMap)
        		.withValueMap(valueMap);
        
        ItemCollection<ScanOutcome> items = null;
        Iterator<Item> iterator = null;
        
        try {
        	
        	items = table.scan(spec);
        	iterator = items.iterator();
        	
        } catch (Exception e) {
        	EventData.info("Unable to read movie");
        	EventData.info(e.getMessage());
        }
        
        return iterator;
	}
	
}
