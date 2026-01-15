package com.lxp.tag.infra.web.internal;

import com.lxp.tag.application.service.TagQueryService;
import com.lxp.tag.application.port.out.dto.TagResult;
import com.lxp.tag.infra.web.external.request.FindTagsByIdsRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
            @Valid
            @NotNull(message = "id 는 null 일 수 없습니다.")
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
            @RequestParam
            @NotNull(message = "id 목록은 null 일 수 없습니다.")
            @NotEmpty(message = "id 목록은 비어있을 수 없습니다.")
            @Valid
            List<Long> ids
    ) {
        return ResponseEntity.ok(tagQueryService.findByIds(ids));
    }

    @GetMapping("/findByName")
    ResponseEntity<TagResult> findByName(
            @RequestParam
            @Valid
            @NotNull(message = "id 는 null 일 수 없습니다.")
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
            @Valid
            @NotBlank
            String q
    ) {
        return ResponseEntity.ok(tagQueryService.searchIdsByNameContaining(q));
    }
}
