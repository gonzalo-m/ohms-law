package com.ilogic.ohmslaw.model;

import java.text.DecimalFormat;

/**
 * Created by G on 8/20/16.
 */
public class Settings {

    private static boolean sAutoRange;

    private static DecimalFormat sDecimalPlaces;
    public static final int MIN_DECIMAL_PLACES = 1;
    public static final int DEFAULT_DECIMAL_PLACES = 2;
    public static final int MAX_DECIMAL_PLACES = 9;

    static {
        setAutoRange(true);
        setDecimalPlaces(DEFAULT_DECIMAL_PLACES);
    }

    public static void setAutoRange(boolean enabled) {
        sAutoRange = enabled;
    }

    public static boolean isAutoRangeOn() {
        return sAutoRange;
    }

    public static void setDecimalPlaces(int decimalPlaces) {
        int numDecimals = DEFAULT_DECIMAL_PLACES;
        if (decimalPlaces >= MIN_DECIMAL_PLACES && decimalPlaces <= MAX_DECIMAL_PLACES) {
            numDecimals = decimalPlaces;
        }

        StringBuilder pattern = new StringBuilder();
        pattern.append("#0.");

        for (int i = 0; i < numDecimals; i++) {
            pattern.append("#");
        }

        sDecimalPlaces = new DecimalFormat(pattern.toString());
    }

    public static DecimalFormat getDecimalPlaces() {
        return sDecimalPlaces;
    }
 }
