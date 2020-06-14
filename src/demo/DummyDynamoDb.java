package demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.services.dynamodbv2.document.Item;

public class DummyDynamoDb implements DynamoDb{

	@Override
	public Iterator<Item> readBirthdayData(String table, String start, String end) throws Exception{
		Item item = new Item();
		item.withStringSet("Birthday", "22-07");
		List<Item> output = new ArrayList<>();
		output.add(item);
		return output.iterator();
	}

	@Override
	public void insertData(String tableName, FriendData friendData)  throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Iterator<Item> ListFriendData(String tableName, String name) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
