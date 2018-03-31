package dofn;

import org.apache.beam.sdk.transforms.DoFn;

import model.Lgbot;
import model.SiteUrlManager;
import twitter4j.Twitter;

/**
 * 連携されたURLをTwitterに投稿する。
 * @author kazuki
 *
 */
public class TwitterPostDoFn extends DoFn<SiteUrlManager, String> {

//	private static Logger LOGGER = LoggerFactory.getLogger(TwitterPostDoFn.class);
	private Twitter lgbot;

	public TwitterPostDoFn(Twitter lgbot) {
		this.lgbot = lgbot;
	}

	@ProcessElement
	public void post(ProcessContext c) {

//		try {
//			lgbot.updateStatus(new String(c.element().getUrl()));
			String publishUrl = new String(c.element().getUrl());
			Lgbot.postToTwitter(this.lgbot, publishUrl);
//			LOGGER.info(String.format("Publish Message : %s", new String(c.element().getUrl())));
//		} catch (TwitterException e) {
//			LOGGER.error(e.getErrorMessage());
//		}
	}

}
