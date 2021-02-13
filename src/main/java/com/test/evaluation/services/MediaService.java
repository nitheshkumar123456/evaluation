package com.test.evaluation.services;

import com.test.evaluation.entity.Media;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {
    Media getMediaByID(Long id);

    Media save(Media media, MultipartFile multipartFile, Long userId);

    Page<Media> getPagedListOfMedia(Long offset, int size);

    Page<Media> getPagedResultForMediaSearch(Long offset, int size, String searchValue);

    Page<Media> getPagedResultForMediaForFileType(String fileType, Long offset, int size);

    List<Media> getMediaForFavoriteByIds(List<Long> ids);

    Media gettingTrendingMedia();
}
