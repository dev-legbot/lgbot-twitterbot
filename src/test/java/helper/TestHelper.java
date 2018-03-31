package helper;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import model.SiteUrlManager;

public class TestHelper {

	public static void assertSiteUrlManager(SiteUrlManager expected, SiteUrlManager actual) {
		assertThat(expected.getUrl(), is(actual.getUrl()));
		assertThat(expected.getSiteType(), is(actual.getSiteType()));
	}
}
