package com.photo.album.controllers;

import com.photo.album.model.ui.AlbumResponseModel;
import com.photo.album.service.AlbumService;
import jakarta.ws.rs.Produces;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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

    @GetMapping(path="/user/{id}",
    produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })

    public List<AlbumResponseModel> getUserAlbums(@PathVariable String id){
        albumService.getAlbums(id).
    }

}
