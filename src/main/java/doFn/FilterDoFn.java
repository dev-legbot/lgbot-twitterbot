package dofn;


import org.apache.beam.sdk.transforms.DoFn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import constants.SiteType;
import model.SiteUrlManager;

/**
 * サイトタイプを取得し、フィルタリングを行う。
 * @author kazuki
 *
 */
public class FilterDoFn extends DoFn<SiteUrlManager, SiteUrlManager> {

	private final Logger LOGGER = LoggerFactory.getLogger(FilterDoFn.class);

	@ProcessElement
	public void Process(ProcessContext c) {

		LOGGER.info(String.format("Receive Message : %s", c.element().getUrl()));
		LOGGER.info(String.format("Receive Attribute : %s", c.element().getSiteType()));

		if(SiteType.isOld(c.element().getSiteType())) {
			c.output(c.element());
		}
	}
}
