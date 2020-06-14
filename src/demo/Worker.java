package demo;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Worker implements Runnable{
	BlockingQueue<? extends Object> queue;
	private final Socket socket;
	
	public Worker(Socket socket) {
		this.socket = socket;
		queue = new ArrayBlockingQueue<>(10);
	}
	
	@Override
	public void run() {
		BufferedReader br = null;
		BufferedOutputStream out = null;
		PrintWriter header = null;
		try {
			
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String headerLine = br.readLine();
			StringTokenizer tokens = new StringTokenizer(headerLine);
			//System.out.println(headerLine);
		    while((headerLine = br.readLine()).length() != 0){
		    }

		    //code to read the post payload data
		    StringBuilder payload = new StringBuilder();
	        while(br.ready()){
	            payload.append((char) br.read());
	            }
	        ObjectMapper mapper = new ObjectMapper();
		    String method = tokens.nextToken();
		    String api = tokens.nextToken();
		    System.out.println(method + ", "+ api );
		  
		    System.out.println("done");
		    if(api.equals("") || api.equals("/")) {
		    	header = new PrintWriter(socket.getOutputStream());
		    	header.println("HTTP/1.1 400 BAD-REQUEST");	
		    	header.println("Access-Control-Allow-Origin: " + "*");
		    	header.println("Content-type: " + "text/plain");
				header.println("Content-length: " + 0);
				header.flush();
				out = new BufferedOutputStream(socket.getOutputStream());
				out.write("".getBytes());
				out.flush();
		    	return;
		    }
		    if(method.equals("GET")) {
		    	if(api.equals("/getZonesList")) {
		    		
		    		header = new PrintWriter(socket.getOutputStream());
					
		    		String res = Utils.getZonesList();

					header.println("HTTP/1.1 200 OK");
					header.println("Access-Control-Allow-Origin: " + "*");
					header.println("Content-type: " + "text/plain");
					header.println("Content-length: " + res.length());
					header.println();
					header.flush();
					out = new BufferedOutputStream(socket.getOutputStream());
					out.write(res.getBytes());
					out.flush();
					
		    	} } else if(api.equals("/listFriends")) {
		    		System.out.println("enter");
		    		String res = Utils.getListFriends(payload.toString());
		    		System.out.println(res);
		    		header = new PrintWriter(socket.getOutputStream());
					

					header.println("HTTP/1.1 200 OK");
					header.println("Access-Control-Allow-Origin: " + "*");
					header.println("Content-type: " + "text/plain");
					header.println("Content-length: " + res.length());
					header.println();
					header.flush();
					out = new BufferedOutputStream(socket.getOutputStream());
					out.write(res.getBytes());
					out.flush();
		    	}
		    	
		     else 
		    	if(api.equals("/addFriends")) {
			        FriendData friendData = mapper.readValue(payload.toString(), FriendData.class);
		    		System.out.println(friendData.toString());
					header = new PrintWriter(socket.getOutputStream());
					String result = "";
					try {
						
					insertDataAndSubscribe(friendData);
					
					header.println("HTTP/1.1 200 OK");
					result = "Successfully updated";
					} catch (Exception e) {
						header.println("HTTP/1.1 400 BAD-REQUEST");	
						result = "Some error. Please try again";
					}
					System.out.println(result);
					header.println("Access-Control-Allow-Origin: " + "*");
					header.println("Content-type: " + "text/plain");
					header.println("Content-length: " + result.length());
					header.println();
					header.flush();
					out = new BufferedOutputStream(socket.getOutputStream());
					out.write(result.getBytes());
					out.flush();
		    	
		    }
			
			
			
		} catch (Exception e) {
			System.out.println("Unexpected Server error: " + e.getMessage());
		} finally {
			try {
				if(br != null) br.close();
				if(out != null) out.close();
				if(socket != null) socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private String[] sort(String data) {
		List<Integer> output = new ArrayList<>();
		for(String i: data.split(",")) {
			String temp = i.trim();
			if (temp.length() == 0)
				continue;
			if(temp.matches("[^0-9]"))
				return new String[] {"0", "Not a valid input"};
			output.add(Integer.parseInt(temp));
		}
		if(output.size() == 0) return new String[] {"0", "No input given"};
		Collections.sort(output);
		String finalData = output.toString();
		return new String[] {"1", finalData.substring(1, finalData.length()-1)};
	}
	
	private void insertDataAndSubscribe(FriendData friendData) throws Exception {
		try {
		Iterator<Item> iter = Builder.db().ListFriendData("BirthDayTable", friendData.getName());
		String topic = "";
		if(iter.hasNext()) {
			Item item = iter.next();
			topic = item.get("Topic").toString();
		} else {
			topic = Builder.sns().createTopic(friendData.getName());
			Builder.sns().subscribeToTopic(topic, friendData.getEmail());
		}
		friendData.setTopicArn(topic);
		Builder.db().insertData("BirthDayTable", friendData);
		} catch (Exception e) {
			EventData.info(e.getMessage());
			throw(e);
		}
	}
	
}
