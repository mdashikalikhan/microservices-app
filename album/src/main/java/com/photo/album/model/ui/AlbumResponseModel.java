package com.photo.album.model.ui;

import lombok.Data;

import java.io.Serializable;

@Data
public class AlbumResponseModel implements Serializable {

    private String albumId;
    private String albumName;
    private String artistName;
    private String userId;
}
