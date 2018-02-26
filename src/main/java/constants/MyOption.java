package constants;

import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

public interface MyOption extends PipelineOptions {

	@Description("My custom Command Line Argument")
	@Default.String("D-FAULT")

	String getSubscription();
	public void setSubscription(String subscription);

	String getConsumerKey();
	public void setConsumerKey(String consumerKey);

	String getConsumerSecret();
	public void setConsumerSecret(String consumerSecret);

	String getAccessToken();
	public void setAccessToken(String accessToken);

	String getAccessTokenSecret();
	public void setAccessTokenSecret(String accessTokenSecret);

}
