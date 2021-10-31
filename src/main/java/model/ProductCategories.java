package model;

import java.util.HashMap;

public enum ProductCategories {
    SMARTPHONE,
    LAPTOP,
    SMART_WATCH;

    private static final HashMap<String, ProductCategories> categories;
    static {
        categories = new HashMap<>();
        for (ProductCategories r : ProductCategories.values()) {
            categories.put(r.toString(), r);
        }
    }

    public static ProductCategories parse(String s) {
        return categories.get(s);
    }
}
