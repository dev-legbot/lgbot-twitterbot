package dofn;

import static helper.TestHelper.*;

import java.util.Arrays;
import java.util.List;

import org.apache.beam.sdk.transforms.DoFnTester;
import org.junit.Before;
import org.junit.Test;

import constants.SiteType;
import helper.TestHelper;
import model.SiteUrlManager;

public class FilterDoFnTest {

	@Before

	@Test
	public void filterTest() throws Exception {

		DoFnTester<SiteUrlManager, SiteUrlManager> tester = DoFnTester.of(new FilterDoFn());

		List<SiteUrlManager> input = Arrays.asList(
			new SiteUrlManager(OLD_SITE_URL, SiteType.OLD.toString()),
			new SiteUrlManager(MODERN_SITE_URL, SiteType.MODERN.toString())
		);

		List<SiteUrlManager> actual = tester.processBundle(input);
		List<SiteUrlManager> expected = Arrays.asList(new SiteUrlManager("http://old.com", "old"));

		for (int i = 0; i < actual.size(); i++) {
			TestHelper.assertSiteUrlManager(actual.get(i), expected.get(i));
		}
	}
}