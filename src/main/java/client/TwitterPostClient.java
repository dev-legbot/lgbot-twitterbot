package client;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import constants.MyOption;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterPostClient implements Client{

	private static Logger LOGGER = LoggerFactory.getLogger(TwitterPostClient.class);
	private static final long serialVersionUID = 1L;
	private TwitterFactory twitterFactory;

	/**
	 * Twitter認証情報の初期化を行う
	 * @param runtimeOption
	 */
	@Override
	public void init(MyOption runtimeOption) {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey(runtimeOption.getConsumerKey())
		  .setOAuthConsumerSecret(runtimeOption.getConsumerSecret())
		  .setOAuthAccessToken(runtimeOption.getAccessToken())
		  .setOAuthAccessTokenSecret(runtimeOption.getAccessTokenSecret());

		this.twitterFactory = new TwitterFactory(cb.build());
	}

	/**
	 * Twitterへ投稿する。
	 * @param oldSiteUrl
	 */
	@Override
	public void post(String oldSiteUrl) {

		if (Objects.isNull(this.twitterFactory)) {
			throw new NullPointerException("TwitterFactoryインスタンスが初期化されていません");
		}

		Twitter lgbot = this.twitterFactory.getInstance();

		try {
			lgbot.updateStatus(oldSiteUrl);
			LOGGER.info(String.format("Post message: %s", oldSiteUrl));
		} catch (TwitterException e) {
			LOGGER.error(String.format("Error message: %s", e.getErrorMessage()));
		}
	}
}
