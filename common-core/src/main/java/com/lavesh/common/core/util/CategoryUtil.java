package com.lavesh.common.core.util;

import com.lavesh.common.core.enums.CategoryEnum;
import org.apache.commons.lang3.ObjectUtils;

public class CategoryUtil {

    public static boolean isValidCategory(String input) {
        if (ObjectUtils.isEmpty(input))
            return false;

        String normalized = input.trim()
                .replaceAll("[\\s\\-]+", "_")
                .replaceAll("_+", "_")
                .toUpperCase();
        for (CategoryEnum category : CategoryEnum.values()) {
            if (category.name().equalsIgnoreCase(normalized)) {
                return true;
            }
        }
        return false;
    }

    public static CategoryEnum fromString(String input) {
        if (ObjectUtils.isEmpty(input))
            return null;

        String normalized = input.trim()
                .replaceAll("[\\s\\-]+", "_")
                .replaceAll("_+", "_")
                .toUpperCase();
        for (CategoryEnum category : CategoryEnum.values()) {
            if (category.name().equalsIgnoreCase(normalized)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid category: " + input);
    }
}

