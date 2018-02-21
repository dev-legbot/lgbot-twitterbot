package doFn;

import java.util.logging.Logger;

import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;
import org.apache.beam.sdk.transforms.DoFn;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * サイトタイプがoldであるサイトのURLをTwitterに投稿する。
 * @author kazuki
 *
 */
public class TwitterPostDoFn extends DoFn<PubsubMessage, String> {

	private String consumerkey;
	private String consumerSecret;
	private String accessToken;
	private String accessTokenSecret;

	public String getConsumerkey() {
		return consumerkey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	/**
	 * コンストラクタ
	 * @param oauth
	 * 		コマンドライン引数で渡したTwitter認証情報の配列
	 */
	public TwitterPostDoFn(String[] oauth) {
		this.consumerkey = oauth[0];
		this.consumerSecret = oauth[1];
		this.accessToken = oauth[2];
		this.accessTokenSecret = oauth[3];
	}

	@ProcessElement
	public void post(ProcessContext c) {

		Logger LOGGER = Logger.getLogger(TwitterPostDoFn.class.getName());

		// Twitter認証情報を初期化する。
		ConfigurationBuilder cb = new ConfigurationBuilder();

		String consumerkey = null;
		String consumerSecret = null;
		String accessToken = null;
		String accessTokenSecret = null;

		cb.setOAuthConsumerKey(consumerkey);
		cb.setOAuthConsumerSecret(consumerSecret);
		cb.setOAuthAccessToken(accessToken);
		cb.setOAuthAccessTokenSecret(accessTokenSecret);


		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter lgbot = tf.getInstance();

		lgbot.setOAuthConsumer(this.consumerkey, this.consumerSecret);
		lgbot.setOAuthAccessToken(new AccessToken(this.accessToken, this.accessTokenSecret));

		try {
			lgbot.updateStatus(new String(c.element().getPayload()));
			LOGGER.info(String.format("Publish Message : %s", new String(c.element().getPayload())));
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

}
