package com.ohgiraffers.api.music;

import java.sql.Connection;
import java.util.List;

import static com.ohgiraffers.common.JDBCTemplate.*;

public class MusicService {

    private final MusicDAO musicDAO = new MusicDAO();

    public List<MusicDTO> findsAllMusics(){

        Connection con = getConnection();
        try{
            return musicDAO.selectAllMusics(con);

        }finally{
            close(con);
        }
    }
}
