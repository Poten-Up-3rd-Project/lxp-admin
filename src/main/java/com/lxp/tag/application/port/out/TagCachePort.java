package com.lxp.tag.application.port.out;

import com.lxp.tag.application.port.out.dto.TagResult;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TagCachePort {

    /**
     * 전체 태그 목록 조회
     */
    List<TagResult> findAll();

    /**
     * ID로 태그 조회
     */
    Optional<TagResult> findById(Long id);

    /**
     * 이름으로 태그 조회
     */
    Optional<Long> findByName(String name);

    /**
     * 여러 ID로 태그 조회
     */
    List<TagResult> findByIds(Collection<Long> ids);

    /**
     * 키워드로 조회
     */
    List<Long> searchIdsByNameContaining(String keyword);
    
    /**
     * 태그 저장 (캐시 갱신)
     */
    void save(TagResult tag);

    /**
     * 전체 태그 캐시 갱신
     */
    void refreshAll(List<TagResult> tags);

    /**
     * 캐시 무효화
     */
    void evictAll();
}
