package constants;

public enum SiteType {
	OLD("old"), MODERN("modern");

	private final String label;

	private SiteType(final String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return this.label;
	}

	public static boolean isOld(String siteType) {
		if (OLD.toString().equals(siteType)) {
			return true;
		}
		return false;
	}
}