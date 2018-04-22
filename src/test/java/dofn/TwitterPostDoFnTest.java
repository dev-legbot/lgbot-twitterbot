package dofn;

import static helper.TestHelper.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.apache.beam.sdk.transforms.DoFnTester;
import org.junit.Test;

import client.Client;
import constants.MyOption;
import constants.SiteType;
import model.SiteUrlManager;

public class TwitterPostDoFnTest {

	private static class MockClient implements Client {

		private String actual;

		public MockClient(String url) {
			this.actual = url;
		}

		@Override
		public void post(String expected) {
			assertThat(actual, is(expected));
		}

		@Override
		public void init(MyOption option) {
		}
	}

	@Test
	public void postTest() throws Exception {
		MockClient client = new MockClient(OLD_SITE_URL);

		DoFnTester<SiteUrlManager, String> tester = DoFnTester.of(new TwitterPostDoFn(client));
		tester.processBundle(new SiteUrlManager(OLD_SITE_URL, SiteType.OLD.toString()));

	}
}
