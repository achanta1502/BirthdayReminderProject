package demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server implements Runnable {
	private ServerSocket server = null;
	private final int PORT;
	private ExecutorService executor;
	private AtomicBoolean quitter;
	public Server(int port, ExecutorService executor, AtomicBoolean quitter) {
		// TODO Auto-generated constructor stub
		this.PORT = port;
		this.executor = executor;
		this.quitter = quitter;
	}
	
	@Override
	public void run(){
		startServer(executor);
	}
	
	public void startServer(ExecutorService executor){
		try {
		server = new ServerSocket(PORT);
		System.out.println("Server started.\n Listetning on port " + PORT);
		
		while(quitter.get()) {
			executor.execute(new Worker(server.accept()));
		}
		
		} catch (IOException e) {
			e.printStackTrace();
			executor.shutdownNow();
			quitter.set(false);
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
