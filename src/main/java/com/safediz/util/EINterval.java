package com.safediz.util;

public enum EINterval {

	OFF(999999999, "OFF"), FIVE(5000, "5s"), THIRTHY(30000, "30s"), MINUTE(60000, "1m"), FMINUTES(300000,
			"5m"), TMINUTES(1800000, "30m"), HOUR(3600000, "1h");

	private EINterval(int ms, String name) {
		this.ms = ms;
		this.name = name;
	}

	private int ms;

	private String name;

	public int getMs() {
		return ms;
	}

	public String getName() {
		return name;
	}
}
