package dofn;

import static helper.TestHelper.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;
import org.apache.beam.sdk.transforms.DoFnTester;
import org.junit.Test;

import helper.TestHelper;
import model.SiteUrlManager;

public class CovertDoFnTest {
	private final DoFnTester<PubsubMessage, SiteUrlManager> tester = DoFnTester.of(new ConvertDoFn());

	@Test
	public void SiteUrlManagerInitTest() throws Exception {

		PubsubMessage input  = TestHelper.createPubsubMessage(OLD_SITE_URL, new HashMap<String, String>());

		List<SiteUrlManager> actual = tester.processBundle(input);
		List<SiteUrlManager> expected = Arrays.asList(new SiteUrlManager("http://old.com", "old"));

		for(int i = 0; i < actual.size(); i++) {
			assertSiteUrlManager(actual.get(i), expected.get(i));
		}
	}

	@Test
	public void SiteUrlManagerNotInitTest() throws Exception {

		PubsubMessage input = TestHelper.createPubsubMessage(OLD_SITE_URL);

		List<SiteUrlManager> actual = tester.processBundle(input);
		List<SiteUrlManager> expected = Arrays.asList(new SiteUrlManager());

		for(int i = 0; i < actual.size(); i++) {
			assertSiteUrlManager(actual.get(i), expected.get(i));
		}
	}
}
