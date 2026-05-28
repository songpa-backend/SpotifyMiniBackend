package com.ohgiraffers.api.likes;

import com.ohgiraffers.api.likes.dto.LikeDTO;
import com.ohgiraffers.api.likes.dto.LikeRequestDTO;
import com.ohgiraffers.api.likes.dto.LikeResponseDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class LikeService {

    private final LikeDAO likeDAO = new LikeDAO();

    public List<LikeDTO> selectLikesByUser(int userId) {
        Connection con = getConnection();

        try {
            return  likeDAO.selectLikesByUser(con, userId);
        } finally {
            close(con);
        }
    }

    public LikeResponseDTO addLike(LikeRequestDTO requestDTO) {
        Connection con = getConnection();
        LikeResponseDTO result = new LikeResponseDTO();

        result.setUserId(requestDTO.getUserId());
        result.setMusicId(requestDTO.getMusicId());

        try {
            con.setAutoCommit(false);

            LikeResponseDTO daoResult = likeDAO.addLike(con, requestDTO.getUserId(), requestDTO.getMusicId());

            if (daoResult != null && daoResult.getLikeId() > 0) {
                con.commit();
                result.setLikeId(daoResult.getLikeId());
                result.setSuccess(true);
                result.setMessage("좋아요 리스트에 추가되었습니다.");
            } else {
                con.rollback();
                result.setSuccess(false);
                result.setMessage("좋아요 추가에 실패했습니다.");
            }
            return result;

        } catch (SQLException e) {
            try { con.rollback(); } catch (Exception se) { se.printStackTrace(); }
            e.printStackTrace();

            result.setSuccess(false);
            if (e.getErrorCode() == 1062) { // 중복 키 에러 (MySQL)
                result.setLikeId(-1);
                result.setMessage("이미 좋아요를 누른 곡입니다.");
            } else {
                result.setLikeId(0);
                result.setMessage("데이터베이스 오류가 발생했습니다.");
            }
            return result;
        } finally {
            close(con);
        }
    }

    public int deleteLike(int likeId) {
        Connection con = getConnection();
        int result = 0;

        try {
            con.setAutoCommit(false);

            result = likeDAO.deleteLike(con, likeId);

            if (result > 0) {
                con.commit();
            } else {
                con.rollback();
            }

        } catch (SQLException e) {
            try { con.rollback(); } catch (SQLException se) { se.printStackTrace(); }
            e.printStackTrace();
        } finally {
            close(con);
        }

        return result;
    }

}
