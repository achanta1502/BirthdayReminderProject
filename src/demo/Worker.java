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
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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
			String headerLine = null;
		    while((headerLine = br.readLine()).length() != 0){
		        System.out.println(headerLine);
		    }

		//code to read the post payload data
		StringBuilder payload = new StringBuilder();
		        while(br.ready()){
		            payload.append((char) br.read());
		            }
		System.out.println("Payload data is: "+payload.toString());
			
			String[] res = sort(payload.toString());
			System.out.println(res[1]);
			header = new PrintWriter(socket.getOutputStream());
			
			if(res[0] == "1") {
			header.println("HTTP/1.1 200 OK");
			} else {
				header.println("HTTP/1.1 400 BAD-REQUEST");	
			}
			header.println("Access-Control-Allow-Origin: " + "*");
			header.println("Content-type: " + "text/plain");
			header.println("Content-length: " + res[1].length());
			header.println();
			header.flush();
			out = new BufferedOutputStream(socket.getOutputStream());
			out.write(res[1].getBytes());
			out.flush();
			
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
}
