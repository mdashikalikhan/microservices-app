package com.photo.album.service;

import com.photo.album.entity.AlbumEntity;

import java.util.List;

public interface AlbumService {
    List<AlbumEntity> getAlbums(String userId);
}
