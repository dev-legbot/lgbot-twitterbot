package doFn;

import java.util.logging.Logger;

import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;
import org.apache.beam.sdk.transforms.DoFn;

import constants.SiteType;

/**
 * サイトタイプを取得し、フィルタリングを行う。
 * @author kazuki
 *
 */
public class FilterDoFn extends DoFn<PubsubMessage, PubsubMessage> {

	@ProcessElement
	public void Process(ProcessContext c) {
		Logger LOGGER = Logger.getLogger(FilterDoFn.class.getName());

		LOGGER.info(String.format("Receive Message : %s", new String(c.element().getPayload())));
		LOGGER.info(String.format("Receive Attribute : %s", c.element().getAttribute("siteType")));

		if(SiteType.judgeSiteType(c.element().getAttribute("siteType"))) {
			c.output(c.element());
		}
	}
}
