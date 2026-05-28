package com.ohgiraffers.api.likes.dto;

public class LikeResponseDTO {
    private int likeId;
    private int userId;
    private int musicId;
    private boolean success;
    private String message;

    public LikeResponseDTO() {}

    public int getLikeId() { return likeId; }
    public void setLikeId(int likeId) { this.likeId = likeId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getMusicId() { return musicId; }
    public void setMusicId(int musicId) { this.musicId = musicId; }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

}
