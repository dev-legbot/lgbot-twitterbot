
import java.util.logging.Logger;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.ParDo;

import constants.MyOption;
import doFn.FilterDoFn;
import doFn.TwitterPostDoFn;

public class Main {

	public static final Logger LOGGER = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) {

		PipelineOptionsFactory.register(MyOption.class);
		MyOption runtimeOptionFromCommandLineArguments = PipelineOptionsFactory.fromArgs(args)
				.withValidation()
				.as(MyOption.class);

		Pipeline p = Pipeline.create(runtimeOptionFromCommandLineArguments);

		p.apply(PubsubIO.readMessagesWithAttributes()
				.fromSubscription(runtimeOptionFromCommandLineArguments.getSubscription()))
				.apply("Filtering only old site", ParDo.of(new FilterDoFn()))
				.apply("Post message to Twitter", ParDo.of(new TwitterPostDoFn(runtimeOptionFromCommandLineArguments)));

		p.run();
	}
}