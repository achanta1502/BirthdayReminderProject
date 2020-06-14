package demo;

import java.util.Iterator;

import com.amazonaws.services.dynamodbv2.document.Item;

public interface DynamoDb {
	public Iterator<Item> readBirthdayData(String table, String start, String end) throws Exception;
	
	public void insertData(String tableName, FriendData friendData) throws Exception;
	
	public Iterator<Item> ListFriendData(String tableName, String name) throws Exception;
	
}
