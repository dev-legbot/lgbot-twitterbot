package dofn;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.apache.beam.sdk.transforms.DoFnTester;
import org.junit.Test;
import org.w3c.dom.DocumentFragment;

import constants.SiteType;
import model.Lgbot;
import model.SiteUrlManager;
import twitter4j.Twitter;
import twitter4j.TwitterAdapter;

class TwitterPostDoFnTest {
	private static class MockClient extends Lgbot{

		private String want;

		public MockClient(String msg) {
			this.want = msg;
		}

		@Override
		public  void postToTwitter(Twitter lgbot, String message) {
			assertThat(message, is(want));
		}
	}

	@Test
	public void post() throws Exception{
		String url = "http://old.com";
		MockClient client = new MockClient(url);

		try(DoFnTester<SiteUrlManager, String> tester = DoFnTester.of(new TwitterPostDoFn(client)){
			tester.processBundle(new SiteUrlManager(url, SiteType.OLD.toString()));
		}
	}


}
