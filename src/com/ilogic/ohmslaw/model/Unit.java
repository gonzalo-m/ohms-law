package com.ilogic.ohmslaw.model;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Unit implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected double quantity;
	protected Prefix prefix;
	protected final Type type;
	
	public Unit(double quantity, Prefix prefix, Type type) {
		this.quantity = quantity;
		this.prefix = prefix;
		this.type = type;
		
		decimalPlaces = new DecimalFormat("#0.##"); // default
		autorange = true; // default
	}
	
	public void setPrefix(Prefix prefix) {
		this.prefix = prefix;
	}
	
	public void setValue(double quantityIn, int prefixMagnitude) {
		double currQuantity = quantityIn;
		int currMagnitude = prefixMagnitude;
		
		// if current magnitude is less than min, set to min
		while (currMagnitude < Prefix.MIN) {
			// convert up
			currMagnitude += 3;
			currQuantity /= 1000;
		}
		// if current magnitude is greater than max, set to max
		while (currMagnitude > Prefix.MAX) {
			// convert down
			currMagnitude -= 3;
			currQuantity *= 1000;
		}
		if (autorange) {
			// keep value between 1 - 999
			while (currQuantity < 1 && currMagnitude > Prefix.NANO.getMagnitude()) {
				// convert down
				currMagnitude -= 3;
				currQuantity *= 1000;
			} 
			
			while (currQuantity >= 1000 && currMagnitude < Prefix.GIGA.getMagnitude()) {
				// convert up
				currMagnitude += 3;
				currQuantity /= 1000;
			}
		} else {
			currQuantity = currQuantity * Math.pow(10, currMagnitude);
			currMagnitude = 0;
		}
		
		quantity = currQuantity;
		prefix = prefix.getPrefix(currMagnitude);
	}
	
	@Override
	public String toString() {
		return toCurrentDecimals() + " " + prefix.getSymbol() + type;
	}
	
	public String toCurrentDecimals() {
		return decimalPlaces.format(quantity);
	}
	
	public void reset() {
		quantity = 0;
		prefix = Prefix.NONE;
	}

	public Prefix getPrefix() {
		return prefix;
	}

	public double getQuantity() {
		return quantity;
	}
	
	/* Settings */
	protected static DecimalFormat decimalPlaces;
	protected static boolean autorange;
	
	public static void setDecimalPlaces(DecimalFormat newDecimalPlaces) {
		decimalPlaces = newDecimalPlaces;
	}
	
	public static void setAutorange(boolean value) {
		autorange = value;
	}
}
