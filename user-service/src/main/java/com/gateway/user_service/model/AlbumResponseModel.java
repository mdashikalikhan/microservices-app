package com.gateway.user_service.model;

import lombok.Data;

@Data
public class AlbumResponseModel {
    private String albumId;
    private String albumName;
    private String artistName;
    private String userId;
}
