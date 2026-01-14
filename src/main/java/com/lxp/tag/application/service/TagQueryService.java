package com.lxp.tag.application.service;

import com.lxp.tag.application.port.out.dto.TagResult;
import com.lxp.tag.application.port.out.TagCachePort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TagQueryService {

    private final TagCachePort tagCachePort;

    public TagQueryService(TagCachePort tagCachePort) {
        this.tagCachePort = tagCachePort;
    }

    public List<TagResult> findAll() {
        return tagCachePort.findAll();
    }

    public Optional<TagResult> findById(Long id) {
        return tagCachePort.findById(id);
    }

    public Optional<TagResult> findByName(String name) {
        Optional<Long> optionalId = tagCachePort.findByName(name);

        if (optionalId.isEmpty()) {
            return Optional.empty();
        }

        return findById(optionalId.get());
    }

    public List<TagResult> findByIds(Collection<Long> ids) {
        return tagCachePort.findByIds(ids);
    }

    public List<TagResult> searchIdsByNameContaining(String keyword) {
        List<Long> longs = tagCachePort.searchIdsByNameContaining(keyword);
        return findByIds(longs);
    }
}
