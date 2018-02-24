package constants;

public enum SiteType{
	old("old"),
	modern("modern");

	private final String type;

	private SiteType(final String text) {
		this.type = text;
	}

	public String toString() {
		return this.type;
	}
}