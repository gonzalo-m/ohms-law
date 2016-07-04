package com.ilogic.ohmslaw.model;

public enum UnitType {

	VOLT(2), AMP(3), OHM(5), WATT(7);

	private final int id;

	private UnitType(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
