package demo;

import java.util.HashMap;
import java.util.Iterator;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;

public class DynamoDbOper {
	private DynamoDB client;
	public DynamoDbOper(String endpoint, String region) {
		AmazonDynamoDB db = AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
				.build();
		this.client = new DynamoDB(db);
	}
	
	public void readData(String tableName, String start, String end) {
		Table table = client.getTable(tableName);
		System.out.print(table.toString());
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#bday", "Birthday");

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":start", new String(start));
        valueMap.put(":end", new String(end));
        
        ScanSpec spec = new ScanSpec().withProjectionExpression("#bday, PersonName")
        		.withFilterExpression("#bday between :start and :end")
        		.withNameMap(nameMap)
        		.withValueMap(valueMap);
        
        ItemCollection<ScanOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;
        
        try {
        	
        	items = table.scan(spec);
        	iterator = items.iterator();
        	while(iterator.hasNext()) {
        		item = iterator.next();
        		System.out.println(item.toJSONPretty());
        	}
        	
        } catch (Exception e) {
        	EventData.info("Unable to read movie");
        	EventData.info(e.getMessage());
        }
	}
}
