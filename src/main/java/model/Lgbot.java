package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import constants.MyOption;
import twitter4j.Twitter;
import twitter4j.TwitterAdapter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class Lgbot extends TwitterAdapter  {

	private static Logger LOGGER = LoggerFactory.getLogger(Lgbot.class);

	public static Twitter getInstance(MyOption runtimeOption) {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey(runtimeOption.getConsumerKey())
		  .setOAuthConsumerSecret(runtimeOption.getConsumerSecret())
		  .setOAuthAccessToken(runtimeOption.getAccessToken())
		  .setOAuthAccessTokenSecret(runtimeOption.getAccessTokenSecret());

		TwitterFactory tf = new TwitterFactory(cb.build());

		return tf.getInstance();
	}

	public static void postToTwitter(Twitter lgbot, String postUrl) {

		try {
			lgbot.updateStatus(postUrl);
			LOGGER.info("post URL %s", postUrl);
		} catch (TwitterException e) {
			LOGGER.error(e.getErrorMessage());
		}
	}
}
