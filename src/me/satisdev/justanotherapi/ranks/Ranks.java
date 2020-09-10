package me.satisdev.justanotherapi.ranks;

public enum Ranks {
	OWNER("\"&4&lOwner\"", 7),
	DEV("\"&5&lDeveloper\"", 6),
	ADMIN("\"&4&lAdmin\"", 5),
	STAFF("\"&3&lStaff\"", 4),
	BUILDER("\"&2&lBuilder\"", 3),
	DONOR("\"&6&lDonor\"", 2),
	DEFAULT("\"None\"", 1);

	private final Object[] values;

	Ranks(Object... value) {
		values = value;
	}

	public String getRank() {
		return (String) values[0];
	}

	public Integer getInt() {
		return (Integer) values[1];
	}
}
