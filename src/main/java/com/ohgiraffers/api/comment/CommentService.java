package com.ohgiraffers.api.comment;


import com.ohgiraffers.api.music.MusicDAO;

import java.sql.Connection;
import java.util.List;

import static com.ohgiraffers.common.JDBCTemplate.*;

public class CommentService {

    private final CommentDAO commentDAO = new CommentDAO();

    public List<CommentDTO> findsAllComments(){

        Connection con = getConnection();
        try{
            return commentDAO.selectAllComments(con);

        }finally{
            close(con);
        }
    }

    public List<CommentDTO> findCommentsByUserAndMusic(int userId, int musicId) {

        Connection con = getConnection();

        try {

            return commentDAO.selectCommentsById(con, userId, musicId);
        } finally {

            close(con);
        }
    }

    public CommentDTO registComment(String content, int music_id, int user_id){

        Connection con = getConnection();

        try{
            return commentDAO.insertComment(con, content, music_id, user_id);
        }finally {
            close(con);
        }
    }
}
