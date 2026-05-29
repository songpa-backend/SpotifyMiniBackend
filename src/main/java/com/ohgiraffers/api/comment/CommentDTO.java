package com.ohgiraffers.api.comment;

public class CommentDTO {

    private int comment_id;
    private String content;
    private int userId;
    private int musicId;

    public CommentDTO() {
    }

    public CommentDTO(int comment_id, String content, int userId, int musicId) {
        this.comment_id = comment_id;
        this.content = content;
        this.userId = userId;
        this.musicId = musicId;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @Override
    public String toString() {
        return "CommentDTO{" +
                "comment_id=" + comment_id +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                ", musicId=" + musicId +
                '}';
    }
}
