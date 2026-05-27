package com.ohgiraffers.api.comment;


import java.sql.Connection;
import java.util.List;

import static com.ohgiraffers.common.JDBCTemplate.*;

public class CommentService {

    private final CommentDAO musicDAO = new CommentDAO();

    public List<CommentDTO> findsAllComments(){

        Connection con = getConnection();
        try{
            return musicDAO.selectAllComments(con);

        }finally{
            close(con);
        }
    }

    public List<CommentDTO> findCommentsByUserAndMusic(int userId, int musicId) {

        Connection con = getConnection();

        try {

            return musicDAO.selectCommentsByUserAndMusic(con, userId, musicId);
        } finally {

            close(con);
        }
    }
}
