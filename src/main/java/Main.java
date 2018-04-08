
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.ParDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.Client;
import client.TwitterPostClient;
import constants.MyOption;
import dofn.ConvertDoFn;
import dofn.FilterDoFn;
import dofn.TwitterPostDoFn;

public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {

		PipelineOptionsFactory.register(MyOption.class);
		MyOption runtimeOption = PipelineOptionsFactory.fromArgs(args).withValidation().as(MyOption.class);

		LOGGER.info(String.format("Runtime Settings : %s", runtimeOption.toString()));

		Client twitter = new TwitterPostClient();
		twitter.init(runtimeOption);

		Pipeline p = Pipeline.create(runtimeOption);

		p.apply(PubsubIO.readMessagesWithAttributes()
		 .fromSubscription(runtimeOption.getSubscription()))
		 .apply("Convert to SiteUrlManager", ParDo.of(new ConvertDoFn()))
    		 .apply("Filtering only old site", ParDo.of(new FilterDoFn()))
		 .apply("Post message to Twitter", ParDo.of(new TwitterPostDoFn(twitter)));

		p.run();
	}
}