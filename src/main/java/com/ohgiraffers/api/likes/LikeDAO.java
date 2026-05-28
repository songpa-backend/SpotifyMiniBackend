package com.ohgiraffers.api.likes;

import com.ohgiraffers.api.likes.dto.LikeDTO;
import com.ohgiraffers.api.likes.dto.LikeResponseDTO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplate.close;

public class LikeDAO {

    private Properties prop = new Properties();

    public LikeDAO() {
        String resourcePath = "mapper/like-query.xml";

        try (InputStream is = LikeService.class.getClassLoader().getResourceAsStream(resourcePath)) {

            if (is == null) {
                throw new FileNotFoundException("클래스패스에서 파일을 찾을 수 없습니다: " + resourcePath);
            }
            prop.loadFromXML(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<LikeDTO> selectLikesByUser(Connection con, int userId) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<LikeDTO> likeList = new ArrayList<>();

        String query = prop.getProperty("selectAllLikesByUser");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, userId);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                likeList.add(new LikeDTO(
                        rset.getInt("like_id"),
                        rset.getInt("user_id"),
                        rset.getInt("music_id"),
                        rset.getString("title"),
                        rset.getString("artist")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        return likeList;
    }

    public LikeResponseDTO addLike(Connection con, int userId, int musicId) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        LikeResponseDTO responseDTO = new LikeResponseDTO();
        int generatedKey = 0;

        String query = prop.getProperty("addLike");

        try {
            pstmt = con.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, musicId);

            int result = pstmt.executeUpdate();

            if (result > 0) {
                rset = pstmt.getGeneratedKeys();
                if (rset.next()) {
                    generatedKey = rset.getInt(1);
                }
            }

            responseDTO.setLikeId(generatedKey);

        } finally {
            close(rset);
            close(pstmt);
        }

        return responseDTO;
    }

    public int deleteLike(Connection con, int likeId) throws SQLException {
        PreparedStatement pstmt = null;
        int result = 0;

        String query = prop.getProperty("deleteLike");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, likeId);

            result = pstmt.executeUpdate();

        } finally {
            close(pstmt);
        }
        return result;
    }
}
