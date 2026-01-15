package com.lxp.tag.application.port.out.dto;

public record TagResult(
        String category,
        String subCategory,
        long tagId,
        String name,
        String state, // ACTIVE, INACTIVE
        String color,
        String variant
) {
}
