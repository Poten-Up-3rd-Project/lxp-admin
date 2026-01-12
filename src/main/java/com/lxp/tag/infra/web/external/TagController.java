package com.lxp.tag.infra.web.external;

import com.lxp.tag.application.service.TagQueryService;
import com.lxp.tag.application.port.out.dto.TagResult;
import com.lxp.tag.infra.web.external.request.FindTagsByIdsRequest;
import com.lxp.tag.infra.web.external.response.TagResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
@RequestMapping("/api-v1/tags")
public class TagController {

    private final TagQueryService tagQueryService;

    public TagController(TagQueryService tagQueryService) {
        this.tagQueryService = tagQueryService;
    }

    @GetMapping
    ResponseEntity<List<TagResponse>> findAll() {
        List<TagResult> tagResults = tagQueryService.findAll();
        List<TagResponse> body = tagResults.stream()
                .map(TagResponse::of)
                .toList();

        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    ResponseEntity<TagResponse> findById(
            @PathVariable
            @Valid
            @NotNull(message = "id 는 null 일 수 없습니다.")
            Long id
    ) {
        Optional<TagResult> optional = tagQueryService.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        TagResponse body = TagResponse.of(optional.get());
        return ResponseEntity.ok(body);
    }

    @GetMapping("/findByIds")
    ResponseEntity<List<TagResponse>> findByIds(
            @RequestBody
            @Valid
            FindTagsByIdsRequest request
    ) {
        List<TagResult> results = tagQueryService.findByIds(request.ids());
        if (results.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<TagResponse> body = results.stream()
                .map(TagResponse::of)
                .toList();
        return ResponseEntity.ok(body);
    }

    @GetMapping("findByName")
    ResponseEntity<Long> findByName(
            @RequestParam
            @Valid
            @NotNull(message = "id 는 null 일 수 없습니다.")
            String name
    ) {
        Optional<Long> optional = tagQueryService.findByName(name);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Long body = optional.get();
        return ResponseEntity.ok(body);
    }

    @GetMapping("/search")
    ResponseEntity<List<TagResponse>> search(
            @RequestParam
            @Valid
            @NotBlank
            String q
    ) {
        List<TagResult> results = tagQueryService.searchIdsByNameContaining(q);
        List<TagResponse> body = results.stream()
                .map(TagResponse::of)
                .toList();
        return ResponseEntity.ok(body);
    }
}
