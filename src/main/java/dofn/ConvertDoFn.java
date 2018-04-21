package dofn;

import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;
import org.apache.beam.sdk.transforms.DoFn;

import model.SiteUrlManager;

public class ConvertDoFn extends DoFn<PubsubMessage, SiteUrlManager> {

	@ProcessElement
	public void convert(ProcessContext c) {

		String url = new String(c.element().getPayload());
		String siteType = c.element().getAttribute("siteType");

		if(url == null || siteType == null) {
			return;
		}

		SiteUrlManager manager = new SiteUrlManager(url, siteType);

		c.output(manager);
	}

}
