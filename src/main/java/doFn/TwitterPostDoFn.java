package doFn;

import java.util.logging.Logger;

import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;
import org.apache.beam.sdk.transforms.DoFn;

import constants.MyOption;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
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

	/**
	 * コンストラクタ
	 * @param option
	 * 		コマンドライン引数で渡したTwitter認証情報
	 */
	public TwitterPostDoFn(MyOption option) {
		this.consumerkey = option.getConsumerKey();
		this.consumerSecret = option.getConsumerSecret();
		this.accessToken = option.getAccessToken();
		this.accessTokenSecret = option.getAccessTokenSecret();
	}

	/**
	 * TwitterBot認証情報をセットする。
	 * @return
	 * 		認証情報をセットしたTwitterインスタンス
	 */
	private Twitter initOauth() {
		ConfigurationBuilder cb = new ConfigurationBuilder();

		cb.setOAuthConsumerKey(this.consumerkey).setOAuthConsumerSecret(this.consumerSecret)
				.setOAuthAccessToken(this.accessToken).setOAuthAccessTokenSecret(this.accessTokenSecret);

		TwitterFactory tf = new TwitterFactory(cb.build());

		return tf.getInstance();
	}

	@ProcessElement
	public void post(ProcessContext c) {

		Logger LOGGER = Logger.getLogger(TwitterPostDoFnTest.class.getName());

		Twitter twitterBotAccount = initOauth();

		try {
			twitterBotAccount.updateStatus(new String(c.element().getPayload()));
			LOGGER.info(String.format("Publish Message : %s", new String(c.element().getPayload())));
		} catch (TwitterException e) {
			LOGGER.warning(e.getErrorMessage());
		}
	}

}
