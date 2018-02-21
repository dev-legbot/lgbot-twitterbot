package doFn;

import java.util.logging.Logger;

import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;
import org.apache.beam.sdk.transforms.DoFn;

/**
 * PubSubメッセージのURLを取得する。
 * @author kazuki
 *
 */
public class ConvertDoFn extends DoFn<PubsubMessage, PubsubMessage> {

	@ProcessElement

	public void process(ProcessContext c) {

		Logger LOGGER = Logger.getLogger(ConvertDoFn.class.getName());

		LOGGER.info(String.format("Receive Message : %s", new String(c.element().getPayload())));
		c.output(c.element());

	}

}
