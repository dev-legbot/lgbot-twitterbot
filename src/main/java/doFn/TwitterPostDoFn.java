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
 * 連携されたURLをTwitterに投稿する。
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

	/**
	 * TwitterBot認証情報の初期化を行う。
	 * @return tf
	 * 		初期化したTwitterFactoryインスタンス
	 */
	private TwitterFactory initOauth() {
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

		return tf;
	}

	@ProcessElement
	public void post(ProcessContext c) {

		Logger LOGGER = Logger.getLogger(TwitterPostDoFn.class.getName());

		Twitter lgbot = initOauth().getInstance();

		lgbot.setOAuthConsumer(this.consumerkey, this.consumerSecret);
		lgbot.setOAuthAccessToken(new AccessToken(this.accessToken, this.accessTokenSecret));

		try {
			lgbot.updateStatus(new String(c.element().getPayload()));
			LOGGER.info(String.format("Publish Message : %s", new String(c.element().getPayload())));
		} catch (TwitterException e) {
			LOGGER.warning(e.getErrorMessage());
		}
	}

}
