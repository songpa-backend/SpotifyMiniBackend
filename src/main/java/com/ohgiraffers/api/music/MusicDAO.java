package com.ohgiraffers.api.music;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.ohgiraffers.common.JDBCTemplate.close;

public class MusicDAO {

    //1. 음악 목록 싹 긁어오기
    public List<MusicDTO> selectAllMusics(Connection con){

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<MusicDTO> musics = new ArrayList<>();

        String query = " SELECT music_id, title, artist, genre, duration FROM musics  ORDER BY music_id DESC ";

        try{
                pstmt = con.prepareStatement(query);
                rset = pstmt.executeQuery();

                while(rset.next()){
                    musics.add(new MusicDTO(
                            rset.getInt("music_id"),
                            rset.getString("title"),
                            rset.getString("artist"),
                            rset.getString("genre"),
                            rset.getInt("duration")
                    ));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                close(rset);
                close(pstmt);
            }
        return musics;
    }
}
