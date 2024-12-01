package com.photo.album.controllers;

import com.photo.album.model.ui.AlbumResponseModel;
import com.photo.album.service.AlbumService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/albums")
@Slf4j
@RequiredArgsConstructor
public class AlbumController {

    @NonNull
    private AlbumService albumService;

    @GetMapping("/user/{id}")
    public List<AlbumResponseModel> getUserAlbums(@PathVariable String id){

    }

}
