package com.ohgiraffers.api.music;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MusicDAO {

    //1. 음악 목록 싹 긁어오기
    public List<MusicDTO> selectAllMusics(Connection con){

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<MusicDTO> musics = new ArrayList<>();

        String query = " SELECT music_id, title, artist, genre, duration FROM spotifymini_db  ORDER BY music_id DESC ";


        return musics;
    }
}
