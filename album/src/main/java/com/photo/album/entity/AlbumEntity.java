package com.photo.album.entity;

import lombok.Data;

@Data
public class AlbumEntity {

    private long id;
    private String albumId;
    private String albumName;
    private String artistName;
    private String userId;
}
