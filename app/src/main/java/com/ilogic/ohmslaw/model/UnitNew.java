package com.ilogic.ohmslaw.model;

/**
 * Created by G on 7/3/16.
 */
public class UnitNew {

    public static final double ZERO = 0.0d;

    private double quantity;
    private String formattedQty;
    private Prefix prefix;

    public UnitNew() {
        this(ZERO, Prefix.NONE);
    }

    public UnitNew(double quantity, Prefix prefix) {
        this.setQuantity(quantity);
        this.setPrefix(prefix);
    }

    public static String format(double qty) {
        return Settings.getDecimalPlaces().format(qty);
    }

    public void setValue(UnitNew unit) {
        this.setQuantity(unit.quantity);
        this.setPrefix(unit.prefix);
    }

    public double getQuantity() {
        return quantity;
    }

    public String getFormattedQty() {
        return formattedQty;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
        this.formattedQty = format(quantity);
    }

    public String getPrefixSymbol() {
        return prefix.getSymbol();
    }

    public Prefix getPrefix() {
        return prefix;
    }

    public void setPrefix(Prefix prefix) {
        this.prefix = prefix;
    }

    public boolean isGreaterThanZero() {
        return isGreaterThanZero(quantity);
    }

    public static boolean isGreaterThanZero(double quantity) {
        return Double.compare(quantity, ZERO) > 0;
    }

    public static UnitNew multiply(UnitNew lhs, UnitNew rhs) {
        double qty = lhs.quantity * rhs.quantity;
        int mag = lhs.prefix.getMagnitude() + rhs.prefix.getMagnitude();
        return newBoundedUnit(qty, mag);
    }

    public static UnitNew divide(UnitNew lhs, UnitNew rhs) {
        if (!isGreaterThanZero(rhs.quantity)) {
            return new UnitNew();
        }
        double qty = lhs.quantity / rhs.quantity;
        int mag = lhs.prefix.getMagnitude() - rhs.prefix.getMagnitude();
        return newBoundedUnit(qty, mag);
    }

    public static UnitNew sqrt(UnitNew unit) {
        double qty = Math.sqrt(unit.quantity * Math.pow(10, unit.prefix.getMagnitude()));
        int mag = 0;
        return newBoundedUnit(qty, mag);
    }

    private static UnitNew newBoundedUnit(double quantity, int magnitude) {
        double currQuantity = quantity;
        int currMagnitude = magnitude;

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

        if (Settings.isAutoRangeOn()) {
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

        return new UnitNew(currQuantity, Prefix.getPrefix(currMagnitude));
    }

    @Override
    public String toString() {
        return "{" + quantity +
                "," + prefix +
                "," + formattedQty +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnitNew unitNew = (UnitNew) o;

        if (formattedQty.compareTo(unitNew.formattedQty) != 0) return false;
        return prefix == unitNew.prefix;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(quantity);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + prefix.hashCode();
        return result;
    }
}
