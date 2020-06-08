package demo;

import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class EventData{
 private final static Logger logger = Logger.getLogger("Birthday server log");
 
 public static void addFilePath(String path) {
	 try {
	 logger.addHandler(new FileHandler(path));
	 } catch (Exception e) {
		 System.out.println("can't create log handler: " + e.getMessage());
	 }
 }

 public static void info(String message) {
	 logger.info(message);
 }
}
