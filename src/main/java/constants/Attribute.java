package constants;

public enum Attribute{
	old("old"),
	modern("modern");

	private final String text;

	private Attribute(final String text) {
		this.text = text;
	}

	public String getString() {
		return this.text;
	}
}