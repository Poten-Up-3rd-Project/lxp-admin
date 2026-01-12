package com.lxp.tag.infra.web.external.response;

import com.lxp.tag.application.dto.TagResult;

public record TagResponse(
        long tagId,
        String name,
        String state, // ACTIVE, INACTIVE
        String color,
        String variant
) {
    public static TagResponse of(TagResult tagResult) {
        if (tagResult == null) {
            throw new NullPointerException();
        }

        return new TagResponse(
                tagResult.tagId(), tagResult.name(), tagResult.state(), tagResult.color(), tagResult.variant()
        );
    }
}
