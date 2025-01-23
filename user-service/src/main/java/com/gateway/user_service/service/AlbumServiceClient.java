package com.gateway.user_service.service;

import com.gateway.user_service.model.AlbumResponseModel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient (value = "album-service", path = "/albums")
public interface AlbumServiceClient {

    @GetMapping("/user/{id}")
    @Retry(name = "album-service")
    @CircuitBreaker(name = "album-service", fallbackMethod = "getAlbumsFallback")
    public ResponseEntity<List<AlbumResponseModel>> getAlbums(@PathVariable("id") String id);

    default ResponseEntity<List<AlbumResponseModel>> getAlbumsFallback(String id,
                                                                       Throwable exception){
        System.out.println("fallback: " + id);
        return ResponseEntity.internalServerError().body(new ArrayList<>());
    }

}
