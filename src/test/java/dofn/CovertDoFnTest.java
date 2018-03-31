package dofn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;
import org.apache.beam.sdk.transforms.DoFnTester;
import org.junit.Before;
import org.junit.Test;

import constants.SiteType;
import helper.TestHelper;
import model.SiteUrlManager;

public class CovertDoFnTest {

	private PubsubMessage input;

	@Before
	public void setUpPubSubMessage() {

		Map<String, String> attribute = new HashMap<String, String>();
		attribute.put("siteType", SiteType.OLD.toString());

		input = new PubsubMessage(("http://old.com").getBytes(), attribute) ;

	}

	@Test
	public void testConvert() throws Exception {

		DoFnTester<PubsubMessage, SiteUrlManager> tester = DoFnTester.of(new ConvertDoFn());

		List<SiteUrlManager> actual = new ArrayList<SiteUrlManager>();

		actual.add(new SiteUrlManager("http://old.com", "old"));
//		actual.add(new SiteUrlManager("", ""));

		List<SiteUrlManager> expected = tester.processBundle(input);

		for(int i = 0; i < expected.size(); i++) {
			TestHelper.assertSiteUrlManager(expected.get(i), actual.get(i));

//			System.out.println(expected.size());
		}

	}

}
