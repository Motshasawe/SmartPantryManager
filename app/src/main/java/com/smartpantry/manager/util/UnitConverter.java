package com.smartpantry.manager.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class UnitConverter {

    private UnitConverter() {
    }

    private static final Map<String, String> UNIT_TO_BASE = new HashMap<>();
    private static final Map<String, Double> TO_BASE_FACTOR = new HashMap<>();

    static {
        UNIT_TO_BASE.put("g", "g");
        UNIT_TO_BASE.put("kg", "g");
        UNIT_TO_BASE.put("ml", "ml");
        UNIT_TO_BASE.put("l", "ml");
        UNIT_TO_BASE.put("oz", "g");
        UNIT_TO_BASE.put("lb", "g");
        UNIT_TO_BASE.put("cup", "ml");
        UNIT_TO_BASE.put("tbsp", "ml");
        UNIT_TO_BASE.put("tsp", "ml");
        UNIT_TO_BASE.put("piece", "piece");
        UNIT_TO_BASE.put("clove", "piece");
        UNIT_TO_BASE.put("slice", "piece");
        UNIT_TO_BASE.put("pinch", "piece");
        UNIT_TO_BASE.put("can", "piece");
        UNIT_TO_BASE.put("bunch", "piece");

        TO_BASE_FACTOR.put("g", 1.0);
        TO_BASE_FACTOR.put("kg", 1000.0);
        TO_BASE_FACTOR.put("ml", 1.0);
        TO_BASE_FACTOR.put("l", 1000.0);
        TO_BASE_FACTOR.put("oz", 28.35);
        TO_BASE_FACTOR.put("lb", 453.59);
        TO_BASE_FACTOR.put("cup", 240.0);
        TO_BASE_FACTOR.put("tbsp", 15.0);
        TO_BASE_FACTOR.put("tsp", 5.0);
        TO_BASE_FACTOR.put("piece", 1.0);
        TO_BASE_FACTOR.put("clove", 1.0);
        TO_BASE_FACTOR.put("slice", 1.0);
        TO_BASE_FACTOR.put("pinch", 1.0);
        TO_BASE_FACTOR.put("can", 1.0);
        TO_BASE_FACTOR.put("bunch", 1.0);
    }

    public static String normalizeUnit(String unit) {
        if (unit == null) {
            return "";
        }
        return unit.trim().toLowerCase(Locale.ROOT);
    }

    public static boolean isKnownUnit(String unit) {
        return UNIT_TO_BASE.containsKey(normalizeUnit(unit));
    }

    public static double convertToBase(double quantity, String unit) {
        Double factor = TO_BASE_FACTOR.get(normalizeUnit(unit));
        if (factor == null) {
            return quantity;
        }
        return quantity * factor;
    }

    public static boolean sameUnitFamily(String unitA, String unitB) {
        String baseA = UNIT_TO_BASE.get(normalizeUnit(unitA));
        String baseB = UNIT_TO_BASE.get(normalizeUnit(unitB));
        if (baseA == null || baseB == null) {
            return normalizeUnit(unitA).equals(normalizeUnit(unitB));
        }
        return baseA.equals(baseB);
    }
}
