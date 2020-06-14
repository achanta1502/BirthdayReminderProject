package demo;

import com.amazonaws.services.sns.AmazonSNS;

public interface Publish {
		
	public String createTopic(String name) throws Exception;
	
	public String subscribeToTopic(String topicArn, String email) throws Exception;
	
	public String publishMsg(String topicArn, String message) throws Exception;
	
	}
