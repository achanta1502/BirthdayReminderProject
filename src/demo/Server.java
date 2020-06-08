package demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

public class Server{
	private ServerSocket server = null;
	private final int PORT;
	public Server(int port) {
		// TODO Auto-generated constructor stub
		this.PORT = port;
	}

	public void startServer(ExecutorService executor) throws Exception{
		try {
		server = new ServerSocket(PORT);
		System.out.println("Server started.\n Listetning on port " + PORT);
		
		while(true) {
			executor.execute(new Worker(server.accept()));
			
		}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ServerSocket getSocket() {
		return server;
	}
	
	public void stopServer()throws Exception {
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
