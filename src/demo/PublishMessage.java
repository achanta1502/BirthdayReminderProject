package demo;

import org.apache.http.client.ClientProtocolException;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.SubscribeResult;

public class PublishMessage implements Publish{
	
	private static AmazonSNS client;
	
	public PublishMessage(String accessKey, String secretKey, String endPoint, String region) {
		AWSCredentials creds = new BasicAWSCredentials(accessKey, secretKey);
		client = AmazonSNSClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(creds))
				.withEndpointConfiguration(new EndpointConfiguration(endPoint, region))
				.build();
	}
	
	public String createTopic(String name) throws Exception {
		CreateTopicRequest topicRequest = new CreateTopicRequest(name);
		CreateTopicResult response = client.createTopic(topicRequest);
		return response.getTopicArn();
	}
	
	public String subscribeToTopic(String topicArn, String email) throws Exception{
		SubscribeRequest request = new SubscribeRequest(topicArn, "email", email);
		SubscribeResult result = client.subscribe(request);
		return result.getSubscriptionArn();
	}
	
	public String publishMsg(String topicArn, String message) throws Exception{
		PublishRequest request = new PublishRequest(topicArn, message);
		PublishResult result = client.publish(request);
		return result.toString();
	}

}
