package constants;

public enum SiteType {
	old("old"), modern("modern");

	private final String label;

	private SiteType(final String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return this.label;
	}

	public static boolean judgeSiteType(String siteTypeOfPubSubMesagesURL) {
		if (old.toString().equals(siteTypeOfPubSubMesagesURL)) {
			return true;
		}
		return false;
	}
}