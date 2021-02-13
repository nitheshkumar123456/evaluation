package com.test.evaluation.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.evaluation.constant.HttpStatusString;
import com.test.evaluation.entity.Media;
import com.test.evaluation.exception.GeneralEvaluationException;
import com.test.evaluation.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MediaServiceImplementation implements MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private UserService userService;


    private final ObjectMapper objectMapper;

    private static final String title = "title";
    private static final String name = "name";
    private static final String id = "id";
    private static final String tag = "tag";
    private static final String metadata = "metadata";
    private static final String path = "$.tags";
    private static final String savedUser = "savedUser";
    private static final String type = "type";


    @Override
    public Media getMediaByID(Long id)
    {
        Optional<Media> mediaModel = mediaRepository.findById(id);

        if (!mediaModel.isPresent()) {
            throw new GeneralEvaluationException(HttpStatus.BAD_REQUEST, HttpStatusString.BAD_REQUEST,
                    "The requested media was not found");
        }
        return mediaModel.get();
    }


    @Override
    public Media save(Media media, MultipartFile multipartFile, Long userId)
    {

        try {

            media.setFile(multipartFile.getBytes());
            media.setSavedUser(userService.getUserById(userId));
        }
        catch (Exception e)
        {
            throw new GeneralEvaluationException(HttpStatus.BAD_REQUEST, HttpStatusString.BAD_REQUEST,
                    "No media found");
        }
        return mediaRepository.save(media);
    }

    @Override
    public Page<Media> getPagedListOfMedia(Long offset, int size)
    {
        Pageable paging = PageRequest.of(offset.intValue(), size);
        Page<Media> MediaEntityList = mediaRepository.findAll(paging);
        if(MediaEntityList.getSize()==0)
        {
            throw new GeneralEvaluationException(HttpStatus.BAD_REQUEST, HttpStatusString.BAD_REQUEST,
                    "No more items are available");

        }
        return  MediaEntityList;
    }

    @Override
    public Page<Media> getPagedResultForMediaSearch(Long offset, int size, String searchValue)
    {
        Pageable paging = PageRequest.of(offset.intValue(), size);
        Page<Media> searchedMediaEntityList = mediaRepository.findAll((Specification<Media>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();


                    predicates.add(criteriaBuilder.equal(root.get(savedUser)
                            .get(name), searchValue));
                    predicates.add(criteriaBuilder.like(root.get(title), "%" + searchValue + "%"));
                    List<Predicate> predicateTags = new ArrayList<>();
                    String[] tagsArray = searchValue.split(",");
                    for (String tag : tagsArray) {
                        try {
                            predicateTags.add(criteriaBuilder.like
                                    (criteriaBuilder.function("JSON_CONTAINS",
                                            String.class,
                                            root.get(metadata),
                                            criteriaBuilder.literal(objectMapper.writeValueAsString(tag)),
                                            criteriaBuilder.literal(path))
                                            , "1"));
                        } catch (JsonProcessingException e) {
                            throw new GeneralEvaluationException("JSON processing Exception");
                        }

                    }
                    predicates.add((criteriaBuilder.or(predicateTags.toArray(new Predicate[predicateTags.size()]))));


            return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
        }, paging);
        if(searchedMediaEntityList.getSize()==0)
        {
            throw new GeneralEvaluationException(HttpStatus.BAD_REQUEST, HttpStatusString.BAD_REQUEST,
                    "No more items are available");

        }

    return searchedMediaEntityList;
    }

    @Override
    public Page<Media> getPagedResultForMediaForFileType(String fileType, Long offset, int size)
    {
        Pageable paging = PageRequest.of(offset.intValue(), size);
        Page<Media> searchedMediaEntityList = mediaRepository.findAll((Specification<Media>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.like(root.get(type), fileType));
            return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
        }, paging);
        if(searchedMediaEntityList.getSize()==0)
        {
            throw new GeneralEvaluationException(HttpStatus.BAD_REQUEST, HttpStatusString.BAD_REQUEST,
                    "No more items are available");

        }
        return searchedMediaEntityList;
    }

    @Override
    public List<Media> getMediaForFavoriteByIds(List<Long> ids)
    {
        List<Media> favoriteList =mediaRepository.findByIdIn(ids);
        if(favoriteList.size()==0)
        {
            throw new GeneralEvaluationException(HttpStatus.BAD_REQUEST, HttpStatusString.BAD_REQUEST,
                    "No more items are available");

        }
     return favoriteList;
    }

    @Override
    public Media gettingTrendingMedia()
    {
      Long trendingId =  userService.gettingUniqueFavoriteFromAllUser();
      //the trending media is a random media from all the users unique favorite list
      return getMediaByID(trendingId);
    }



}
