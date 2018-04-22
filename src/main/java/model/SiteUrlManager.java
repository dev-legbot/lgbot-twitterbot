package model;

import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;

@DefaultCoder(AvroCoder.class)

public class SiteUrlManager {

	private String url;
	private String siteType;

	public SiteUrlManager() {
		super();
	}

	public SiteUrlManager(String url, String siteType) {
		this.url = url;
		this.siteType = siteType;
	}


	public String getUrl() {
		return url;
	}


	public String getSiteType() {
		return siteType;
	}

}
