package com.photo.album.service;

import com.photo.album.entity.AlbumEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {
    @Override
    public List<AlbumEntity> getAlbums(String userId) {

        AlbumEntity album1 = new AlbumEntity();
        album1.setUserId(userId);
        album1.setAlbumName("Album Name 1");
        album1.setAlbumId("Album ID 1");
        album1.setArtistName("Artist Name 1");
        album1.setId(1);

        AlbumEntity album2 = new AlbumEntity();
        album2.setUserId(userId);
        album2.setAlbumName("Album Name 2");
        album2.setAlbumId("Album ID 2");
        album2.setArtistName("Artist Name 2");
        album2.setId(2);

        return List.of(album1, album2);
    }
}
