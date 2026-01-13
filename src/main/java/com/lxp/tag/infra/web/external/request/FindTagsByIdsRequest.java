package com.lxp.tag.infra.web.external.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record FindTagsByIdsRequest(
        @NotNull(message = "id 목록은 null 일 수 없습니다.")
        @NotEmpty(message = "id 목록은 비어있을 수 없습니다.")
        List<Long> ids
) {
}
