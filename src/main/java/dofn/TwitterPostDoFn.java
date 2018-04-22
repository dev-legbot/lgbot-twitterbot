package dofn;

import org.apache.beam.sdk.transforms.DoFn;

import client.Client;
import model.SiteUrlManager;

/**
 * 連携されたURLをTwitterに投稿する。
 * @author kazuki
 *
 */
public class TwitterPostDoFn extends DoFn<SiteUrlManager, String> {

	private Client client;

	public TwitterPostDoFn(Client client) {
		this.client = client;
	}

	@ProcessElement
	public void post(ProcessContext c) {

		String oldSiteUrl = new String(c.element().getUrl());
		this.client.post(oldSiteUrl);
		c.output(oldSiteUrl);
	}
}
