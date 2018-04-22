package helper;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;

import constants.SiteType;
import model.SiteUrlManager;

public class TestHelper {
	public static final String OLD_SITE_URL ="http://old.com";
	public static final String MODERN_SITE_URL = "http://modern.com";


	public static void assertSiteUrlManager(SiteUrlManager actual, SiteUrlManager expected) {
		assertThat(actual.getUrl(), is(expected.getUrl()));
		assertThat(actual.getSiteType(), is(expected.getSiteType()));
	}

	public static PubsubMessage createPubsubMessage(String payload) {

		byte[] binarizedPayload = payload.getBytes();
		Map<String, String> attribute = new HashMap<String, String>();

		return new PubsubMessage(binarizedPayload, attribute);
	}

	public static PubsubMessage createPubsubMessage(String payload, Map<String, String> attribute) {

		byte[] binarizedPayload = payload.getBytes();
		attribute.put("siteType", SiteType.OLD.toString());

		return new PubsubMessage(binarizedPayload, attribute);
	}
}
