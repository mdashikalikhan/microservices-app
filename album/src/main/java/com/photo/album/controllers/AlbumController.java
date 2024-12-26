package com.photo.album.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photo.album.entity.AlbumEntity;
import com.photo.album.model.ui.AlbumResponseModel;
import com.photo.album.service.AlbumService;
import jakarta.ws.rs.Produces;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/albums")
@Slf4j
@RequiredArgsConstructor
public class AlbumController {

    @NonNull
    private AlbumService albumService;

    /*@GetMapping(path="/user/{id}",
    produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })*/
    @GetMapping("/user/{id}")
    public ResponseEntity<List<AlbumResponseModel>> getUserAlbums(@PathVariable("id") String id){
        List<AlbumEntity> albumEntities = Optional.ofNullable(albumService.getAlbums(id)).filter(album -> !album.isEmpty())
                .orElse(new ArrayList<>());
        ModelMapper modelMapper = new ModelMapper();
        List<AlbumResponseModel> albumResponseModels = albumEntities.stream().map(album -> modelMapper.map(album, AlbumResponseModel.class)).collect(Collectors.toList());
        return ResponseEntity.ok( albumResponseModels);
    }

}
