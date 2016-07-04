package com.ilogic.ohmslaw.model;

public enum Prefix {
	
	NANO('n', -9),
	MICRO('\u03BC', -6),
	MILLI('m', -3),
	NONE('-', 0),
	KILO('k', 3),
	MEGA('M', 6),
	GIGA('G', 9);
	
	public static final int MIN = -9; // nano
	public static final int MAX = 9;  // GIGA
	
	private char symbol;
	private int magnitude;
	
	Prefix(char symbol, int magnitude) {
		this.symbol = symbol;
		this.magnitude = magnitude;
	}
	
	public char getSymbol() {
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
		return null;
	}
}
