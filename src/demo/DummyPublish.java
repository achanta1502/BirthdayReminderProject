package demo;

import com.amazonaws.services.sns.AmazonSNS;

public class DummyPublish implements Publish{


	@Override
	public String createTopic(String name) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String subscribeToTopic(String topicArn, String email) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String publishMsg(String topicArn, String message) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
