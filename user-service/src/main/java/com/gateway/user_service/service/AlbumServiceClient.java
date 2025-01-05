package com.gateway.user_service.service;

import com.gateway.user_service.model.AlbumResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient (value = "album-service", path = "/albums")
public interface AlbumServiceClient {

    @GetMapping("/user/{id}")
    public ResponseEntity<List<AlbumResponseModel>> getAlbums(@PathVariable("id") String id);

}
