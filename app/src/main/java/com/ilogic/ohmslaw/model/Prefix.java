package com.ilogic.ohmslaw.model;

public enum Prefix {
	
	NANO("n", -9),
	MICRO("\u03BC", -6),
	MILLI("m", -3),
	NONE("", 0),
	KILO("k", 3),
	MEGA("M", 6),
	GIGA("G", 9);
	
	public static final int MIN = -9; // nano
	public static final int MAX = 9;  // GIGA
	
	private String symbol;
	private int magnitude;
	
	Prefix(String symbol, int magnitude) {
		this.symbol = symbol;
		this.magnitude = magnitude;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public int getMagnitude() {
		return magnitude;
	}
	
	public static Prefix getPrefix(int magnitudeIn) {
		for (Prefix p : Prefix.values()) {
			if (p.getMagnitude() == magnitudeIn) 
				return p;
		}
		return NONE;
	}

	public static Prefix getPrefix(String symbol) {
		for (Prefix p : Prefix.values()) {
			if (p.getSymbol().equals(symbol))
				return p;
		}
		return NONE;
	}
}
