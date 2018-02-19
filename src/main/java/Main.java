


import java.util.logging.Logger;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.ParDo;

import doFn.ConvertDoFn;
import doFn.FilterDoFn;
import doFn.TwitterPostDoFn;


public class Main {

	public static final Logger LOGGER = Logger.getLogger(Main.class.getName());

	private static interface MyOption extends PipelineOptions {
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

	private static String[] getOauth(MyOption option) {

		String[] oauth = new String[] {
				option.getConsumerKey(),
				option.getConsumerSecret(),
				option.getAccessToken(),
				option.getAccessTokenSecret()
		};
		return oauth;
	}

	public static void main(String[] args) {

		PipelineOptionsFactory.register(MyOption.class);
		MyOption option = PipelineOptionsFactory.fromArgs(args)
				.withValidation()
				.as(MyOption.class);

		Pipeline p = Pipeline.create(option);

		p.apply(PubsubIO.readMessagesWithAttributes()
				.fromSubscription(option.getSubscription()))

				.apply("Convert to Some Object", ParDo.of(new ConvertDoFn()))
				.apply("Filtering only old site", ParDo.of(new FilterDoFn()))
				.apply("Post message to Twitter", ParDo.of(new TwitterPostDoFn(getOauth(option))));

		p.run();
	}
}