package com.test.evaluation.controllers;

import com.test.evaluation.entity.Media;
import com.test.evaluation.services.MediaService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/media")
@RequiredArgsConstructor
@Slf4j
public class MediaController {
    private static final Integer DEFAULT_LIMIT_SIZE = 10;

    @Autowired
    private MediaService mediaService;


    @GetMapping(path = "/paged", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("method gives list of paged image and video ")
    public ResponseEntity<Page<Media>> getPagedMediaList(@RequestParam(name = "offset", required = false) final Long offset,
                                         @RequestParam(required = false) final Integer size) {


        return new ResponseEntity<Page<Media>>( mediaService.getPagedListOfMedia(offset,(size == null) ? DEFAULT_LIMIT_SIZE : size),HttpStatus.OK);
    }


    @GetMapping(path = "/paged/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("method gives list of paged image and video for searched items user can search on title metadata ")
    public ResponseEntity<Page<Media>> getPagedMediaListForSearch(@RequestParam(name = "offset", required = false) final Long offset,
                                                         @RequestParam(required = false) final Integer size,
                                                         @RequestParam(name="search",required = false) final String search) {


        return new ResponseEntity<Page<Media>>(mediaService.getPagedResultForMediaSearch(offset,(size == null ? DEFAULT_LIMIT_SIZE : size),search),HttpStatus.OK);
    }


    @GetMapping(path = "/paged/search/filetype", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("method gives list of paged media depend on type of media ")
    public ResponseEntity<Page<Media>> getPagedMediaListForFileType(@RequestParam(name = "offset", required = false) final Long offset,
                                                         @RequestParam(required = false) final Integer size,
                                                         @RequestParam(name="filetype",required = false) final String fileType) {


        return new ResponseEntity<Page<Media>>(mediaService.getPagedResultForMediaForFileType(fileType,offset,(size == null ? DEFAULT_LIMIT_SIZE : size)),HttpStatus.OK);
    }


    @GetMapping(path = "/favorite/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("method gives list of favorite media for logged user ")
    public ResponseEntity<List<Media>> getFavoriteMediaForUser(@RequestBody List<Long> favoriteIds) {


        return new ResponseEntity<List<Media>>(mediaService.getMediaForFavoriteByIds(favoriteIds),HttpStatus.OK);
    }

    @GetMapping(path = "/getById/{mediaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("method gives media for  id can be use for click to view media")
    public ResponseEntity<Media> getMediaById(@PathVariable("mediaId") Long mediaId) {


        return new ResponseEntity<Media>(mediaService.getMediaByID(mediaId),HttpStatus.OK);
    }

    @PostMapping(path = "user/{userid}",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("method used to post media and client should send file in request parameter")
    public ResponseEntity<Media> postMedia(@RequestBody Media media,@RequestParam("file") MultipartFile file,@RequestParam Long userId)
    {
        return new ResponseEntity<Media>(mediaService.save(media,file,userId),HttpStatus.OK);
    }


    @GetMapping("trending")
    @ApiOperation("method used to get trending media.the logic used explained in evry method")
    public ResponseEntity<Media> postMedia()
    {
        return new ResponseEntity<Media>(mediaService.gettingTrendingMedia(),HttpStatus.OK);
    }

}
