package client;

import constants.MyOption;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterPostClient implements Client{

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
	  *   TODO エラーログ処理をTwitterPostDoFnで実行したい,throwsで呼び出し元に投げる
	  *   インターフェース定義に反する、 且つ、DoFnは処理の流れだけを書き、シンプルにしたい。
	  */

	/**
	 * Twitterへ投稿する。
	 * @param oldSiteUrl
	 */
	@Override
	public void post(String oldSiteUrl) {

		if(this.twitterFactory == null) {
				throw new NullPointerException("TwitterFactoryインスタンスが初期化されていません");
		}

		Twitter lgbot = this.twitterFactory.getInstance();
		try {
			lgbot.updateStatus(oldSiteUrl);
		} catch (TwitterException e) {
//			TwitterPostDoFn.post(e.getErrorMessage());
		}
	}
}
