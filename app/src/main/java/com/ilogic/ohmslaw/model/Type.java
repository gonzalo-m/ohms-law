package com.ilogic.ohmslaw.model;

public enum Type {
	
	VOLT(2), AMP(3), OHM(5), WATT(7);
	
	private final int id;
	
	Type(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
