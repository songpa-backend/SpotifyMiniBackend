package com.ohgiraffers.api.comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.ohgiraffers.common.JDBCTemplate.close;

public class CommentDAO {

    //1. 댓글 목록 싹 긁어오기
    public List<CommentDTO> selectAllComments(Connection con){

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<CommentDTO> comments = new ArrayList<>();

        String query = " SELECT comment_id, content, user_id, music_id FROM comments  ";

        try{
                pstmt = con.prepareStatement(query);
                rset = pstmt.executeQuery();

                while(rset.next()){
                    comments.add(new CommentDTO(
                            rset.getInt("comment_id"),
                            rset.getString("content"),
                            rset.getInt("user_id"),
                            rset.getInt("music_id")
                    ));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                close(rset);
                close(pstmt);
            }
        return comments;
    }
    public List<CommentDTO> selectCommentsById(Connection con, int userId, int musicId) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<CommentDTO> commentList = new java.util.ArrayList<>();

        String query = "SELECT comment_id, content, user_id, music_id FROM comments WHERE user_id = ? AND music_id = ?";

        try {
            pstmt = con.prepareStatement(query);

            pstmt.setInt(1, userId);
            pstmt.setInt(2, musicId);

            rset = pstmt.executeQuery();

            while (rset.next()) {
                CommentDTO comment = new CommentDTO();

                comment.setComment_id(rset.getInt("comment_id"));
                comment.setUser_id(rset.getInt("user_id"));
                comment.setMusic_id(rset.getInt("music_id"));
                comment.setContent(rset.getString("content"));

                commentList.add(comment);
            }

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }

        return commentList;
    }


}
