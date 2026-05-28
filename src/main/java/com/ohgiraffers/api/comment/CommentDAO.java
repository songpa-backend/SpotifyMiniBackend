package com.ohgiraffers.api.comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.ohgiraffers.common.JDBCTemplate.close;

public class CommentDAO {

    //1. 댓글 목록 싹 긁어오기
    public List<CommentDTO> selectAllComments(Connection con) {

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<CommentDTO> comments = new ArrayList<>();

        String query = " SELECT comment_id, content, user_id, music_id FROM comments  ";

        try {
            pstmt = con.prepareStatement(query);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                comments.add(new CommentDTO(
                        rset.getInt("comment_id"),
                        rset.getString("content"),
                        rset.getInt("user_id"),
                        rset.getInt("music_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        return comments;
    }

    //2. 새 댓글 조회
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

    //3. 새 댓글 등록하기
    public CommentDTO insertComment(Connection con, String content, int user_id, int music_id) {
        System.out.println("(insertComment 실행)"+"content:"+content+"music_id:"+music_id);
        PreparedStatement pstmt = null;
        ResultSet generatedKeys = null;

        String query = " INSERT INTO comments(content, user_id, music_id) VALUES (?,?,?) ";

        try {
            pstmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, content);
            pstmt.setInt(2, user_id);
            pstmt.setInt(3, music_id);
            pstmt.executeUpdate();

            //Auto Increment comment_id값을 얻어오기
            int comment_id = 0;

            generatedKeys = pstmt.getGeneratedKeys();

            if (generatedKeys.next()) {
                comment_id = generatedKeys.getInt(1);
            } else{
                throw new SQLException("생성된 Spotify_db를 읽을 수 없습니다.");
            }

            CommentDTO commentDTO = new CommentDTO(comment_id, content, user_id, music_id);
            return commentDTO;

        } catch (SQLException e) {
            e.printStackTrace();
            //에러 폭탄을 위로 던지면서 이 함수를 강제로 종료
            throw new RuntimeException(e);
        } finally {
            close(generatedKeys);
            close(pstmt);
        }
    }
}