package com.ohgiraffers.api.likes.dto;

public class LikeRequestDTO {

    private int userId;
    private int musicId;

    public LikeRequestDTO() {
    }

    public LikeRequestDTO(int userId, int musicId) {
        this.userId = userId;
        this.musicId = musicId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMusicId() {
        return musicId;
    }

    public void setMusicId(int musicId) {
        this.musicId = musicId;
    }

}
