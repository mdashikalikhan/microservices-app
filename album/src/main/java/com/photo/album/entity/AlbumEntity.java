package com.photo.album.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class AlbumEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String albumId;
    private String albumName;
    private String artistName;
    private String userId;
}
