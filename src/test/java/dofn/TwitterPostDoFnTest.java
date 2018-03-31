package dofn;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.apache.beam.sdk.transforms.DoFnTester;
import org.junit.jupiter.api.Test;

import client.Client;
import constants.MyOption;
import constants.SiteType;
import model.SiteUrlManager;

class TwitterPostDoFnTest {

	private static class MockClient implements Client{

		private String expected;

		public MockClient(String url) {
			this.expected = url;
		}

		@Override
		public void post(String actual) {
			assertThat(expected, is(actual));
		}

		// TODO init処理は今回のテスト観点では必要ないため実装無しでオーバーライド
		@Override
		public void init(MyOption option) {
		}
	}

	@Test
	public void testPost() throws Exception{
		String url = "http://old.com";
		MockClient client = new MockClient(url);

		try(DoFnTester<SiteUrlManager, String> tester = DoFnTester.of(new TwitterPostDoFn(client))){
			tester.processBundle(new SiteUrlManager(url, SiteType.OLD.toString()));
		}
	}
}
