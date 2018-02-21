package doFn;

import static constants.Attribute.*;

import java.util.logging.Logger;

import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;
import org.apache.beam.sdk.transforms.DoFn;

/**
 * サイトのタイプを取得する。oldの場合、Pcollectionを出力する。
 * @author kazuki
 *
 */
public class FilterDoFn extends DoFn<PubsubMessage, PubsubMessage> {

	@ProcessElement
	public void Process(ProcessContext c) {
		Logger LOGGER = Logger.getLogger(FilterDoFn.class.getName());

		LOGGER.info(String.format("Receive Attribute : %s", c.element().getAttribute("siteType")));

		if (c.element().getAttribute("siteType").equals(old.getString())) {
			c.output(c.element());
		}
	}
}
