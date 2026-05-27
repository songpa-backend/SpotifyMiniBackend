package com.ohgiraffers.api.comment;

public class CommentDTO {

    private int comment_id;
    private String content;
    private int user_id;
    private int music_id;

    public CommentDTO() {
    }

    public CommentDTO(int comment_id, String content, int user_id, int music_id) {
        this.comment_id = comment_id;
        this.content = content;
        this.user_id = user_id;
        this.music_id = music_id;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getMusic_id() {
        return music_id;
    }

    public void setMusic_id(int music_id) {
        this.music_id = music_id;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "comment_id=" + comment_id +
                ", content='" + content + '\'' +
                ", user_id=" + user_id +
                ", music_id='" + music_id + '\'' +
                '}';
    }
}
