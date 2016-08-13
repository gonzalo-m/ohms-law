package com.ilogic.ohmslaw.model;

import java.util.Observable;

import com.ilogic.ohmslaw.R;

/**
 * @deprecated
 */
public class OhmsLawModel extends Observable {
	
	private Unit voltage, current, resistance, power;
	
	public OhmsLawModel() {
		setVoltage(new Unit(0, Prefix.NONE, Type.VOLT));
		setCurrent(new Unit(0, Prefix.NONE, Type.AMP));
		setResistance(new Unit(0, Prefix.NONE, Type.OHM));
		setPower(new Unit(0, Prefix.NONE, Type.WATT));
	}
	
	public void setQuantity(int editTextId, int quantityIn) {
		switch (editTextId) {
			case R.id.volts_edittext:
				voltage.quantity = quantityIn;
				break;
			case R.id.amps_edittext:
				current.quantity = quantityIn;
				break;
			case R.id.ohms_edittext:
				resistance.quantity = quantityIn;
				break;
			case R.id.watts_edittext:
				power.quantity = quantityIn;
				break;
		}
	}
	
	public void setPrefix(int editTextId, Prefix prefixIn) {
		switch (editTextId) {
			case R.id.volts_edittext:
				voltage.prefix = prefixIn;
				break;
			case R.id.amps_edittext:
				current.prefix = prefixIn;
				break;
			case R.id.ohms_edittext:
				resistance.prefix = prefixIn;
				break;
			case R.id.watts_edittext:
				power.prefix = prefixIn;
				break;
		}
	}
	
	public void setVoltage(Unit voltage) {
		this.voltage = voltage;
	}

	public void setCurrent(Unit current) {
		this.current = current;
	}

	public void setResistance(Unit resistance) {
		this.resistance = resistance;
	}

	public void setPower(Unit power) {
		this.power = power;
	}
	
	public Unit getVoltage() {
		return voltage;
	}
	
	public Unit getCurrent() {
		return current;
	}
	
	public Unit getResistance() {
		return resistance;
	}
	
	public Unit getPower() {
		return power;
	}
	
	public void setVoltage(double voltageQuantity) {
		this.voltage.quantity = voltageQuantity;
	}

	public void setCurrent(double currentQuantity) {
		this.current.quantity = currentQuantity;
	}

	public void setResistance(double resistanceQuantity) {
		this.resistance.quantity = resistanceQuantity;
	}

	public void setPower(double powerQuantity) {
		this.power.quantity = powerQuantity;
	}

	/**
	 * 
	 * @return true if the object has two valid values (greater than zero),
	 * 			false otherwise
	 */
	public boolean hasTwoValidValues() {
		int count = 0;
		if (voltage.quantity > 0) {
			count++;
		}
		if (current.quantity > 0) {
			count++;
		}
		if (count == 2) {
			return true;
		}
		if (resistance.quantity > 0) {
			count++;
		}
		if (count == 2) {
			return true;
		}
		if (power.quantity > 0) {
			count++;
		}
		if (count == 2) {
			return true;
		}
		return false;	
	}
	
	/**
	 * Performs calculation to obtain unknown values. This method assumes that
	 * 		hasTwoValidValues() has been called previously to check if 
	 * 		the object has two valid values (greater than zero)
	 * 
	 * @param idCombination
	 * 			- parameter that represents the values to be used for calculating
	 * 			other unknown values (based on ids)
	 */
	public void calculateUnknownValues(int idCombination) {
		switch (idCombination) {
			case 6:
				// calculate resistance and power
				// r = v / i
				resistance.setValue(voltage.quantity / current.quantity, 
						voltage.prefix.getMagnitude() - current.prefix.getMagnitude());
				// p = v * i
				power.setValue(voltage.quantity * current.quantity,
						voltage.prefix.getMagnitude() + current.prefix.getMagnitude());
				break;
			case 10:
				// calculate current and power
				// i = v / r;
				current.setValue(voltage.quantity / resistance.quantity,
						voltage.prefix.getMagnitude() - resistance.prefix.getMagnitude());
				// p = v^2 / r
				power.setValue((voltage.quantity * voltage.quantity) / resistance.quantity,
						voltage.prefix.getMagnitude() + voltage.prefix.getMagnitude() 
						- resistance.prefix.getMagnitude());
				break;
			case 14:
				// calculate current and resistance
				// i = p / v
				current.setValue(power.quantity / voltage.quantity,
						power.prefix.getMagnitude() - voltage.prefix.getMagnitude());
				// r = v^2 / p
				resistance.setValue((voltage.quantity * voltage.quantity) / power.quantity,
						voltage.prefix.getMagnitude() + voltage.prefix.getMagnitude()
						- power.prefix.getMagnitude());
				break;
			case 15:
				// calculate voltage and power
				// v = i * r
				voltage.setValue(current.quantity * resistance.quantity,
						current.prefix.getMagnitude() + resistance.prefix.getMagnitude());
				// p = i^2 * r
				power.setValue((current.quantity * current.quantity) * resistance.quantity,
						current.prefix.getMagnitude() + current.prefix.getMagnitude()
						+ resistance.prefix.getMagnitude());
				break;
			case 21:
				// calculate voltage and resistance
				// v = p / i
				voltage.setValue(power.quantity / current.quantity,
						power.prefix.getMagnitude() - current.prefix.getMagnitude());
				// r = p / i^2
				resistance.setValue(power.quantity / (current.quantity * current.quantity),
						power.prefix.getMagnitude() - (current.prefix.getMagnitude()
						+ current.prefix.getMagnitude()));
				break;
			case 35:
				// calculate voltage and current
				// v = sqrt(p * r)
				voltage.setValue(Math.sqrt((power.quantity * Math.pow(10, power.prefix.getMagnitude()))
						* (resistance.quantity*Math.pow(10, resistance.prefix.getMagnitude()))), 0);
				// i = sqrt(p / r)
				current.setValue(Math.sqrt((power.quantity * Math.pow(10, power.prefix.getMagnitude())) 
						/ (resistance.quantity * Math.pow(10, resistance.prefix.getMagnitude()))), 0);
				break;
			}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Resets object's values to zero
	 */
	public void reset() {
		voltage.reset();
		current.reset();
		resistance.reset();
		power.reset();
	}
	
	@Override
	public String toString() {
		return "OhmsLawModel [voltage=" + voltage + ", current=" + current
				+ ", resistance=" + resistance + ", power=" + power + "]";
	}
}
