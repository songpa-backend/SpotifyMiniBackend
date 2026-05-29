package com.ohgiraffers.api.likes.dto;

public class LikeDTO {

    private int likeId;
    private int userId;
    private int musicId;
    private String musicTitle;
    private String artist;

    public LikeDTO(int likeId, int userId, int musicId, String musicTitle, String artist) {
        this.likeId = likeId;
        this.userId = userId;
        this.musicId = musicId;
        this.musicTitle = musicTitle;
        this.artist = artist;
    }

    public int getLikeId() {
        return likeId;
    }

    public int getUserId() {
        return userId;
    }

    public int getMusicId() {
        return musicId;
    }

    public String getMusicTitle() {
        return musicTitle;
    }

    public String getArtist() {
        return artist;
    }

}
