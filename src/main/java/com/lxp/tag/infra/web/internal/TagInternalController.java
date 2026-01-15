package com.lxp.tag.infra.web.internal;

import com.lxp.tag.application.service.TagQueryService;
import com.lxp.tag.application.port.out.dto.TagResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/internal/api-v1/tags")
public class TagInternalController {

    private final TagQueryService tagQueryService;

    public TagInternalController(TagQueryService tagQueryService) {
        this.tagQueryService = tagQueryService;
    }

    @GetMapping
    ResponseEntity<List<TagResult>> findAll() {
        return ResponseEntity.ok(tagQueryService.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<TagResult> findById(
            @PathVariable
            @Positive(message = "id 는 1 이상이어야합니다.")
            Long id
    ) {
        Optional<TagResult> optional = tagQueryService.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optional.get());
    }

    @GetMapping("/findByIds")
    ResponseEntity<List<TagResult>> findByIds(
            @RequestParam(value = "ids", required = false)
            @NotNull(message = "id 목록은 null 일 수 없습니다.")
            @Size(min = 1, message = "id 목록은 비어있을 수 없습니다.")
            List<@NotNull Long> ids
    ) {

        return ResponseEntity.ok(tagQueryService.findByIds(ids));
    }

    @GetMapping("/findByName")
    ResponseEntity<TagResult> findByName(
            @RequestParam
            @NotBlank(message = "id 는 null 이거나 비어있을 수 없습니다.")
            String name
    ) {
        Optional<TagResult> optional = tagQueryService.findByName(name);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        TagResult body = optional.get();
        return ResponseEntity.ok(body);
    }

    @GetMapping("/search")
    ResponseEntity<List<TagResult>> search(
            @RequestParam
            @NotBlank(message = "검색 키워드는 null 이거나 비어있을 수 없습니다.")
            String q
    ) {
        return ResponseEntity.ok(tagQueryService.searchIdsByNameContaining(q));
    }
}
