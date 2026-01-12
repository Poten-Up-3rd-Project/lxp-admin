package com.lxp.tag.application.dto;

public record TagResult(
        long tagId,
        String name,
        String state, // ACTIVE, INACTIVE
        String color,
        String variant
) {
}
