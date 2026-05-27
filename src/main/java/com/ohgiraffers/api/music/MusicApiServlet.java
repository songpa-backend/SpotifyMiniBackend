package com.ohgiraffers.api.music;

import com.ohgiraffers.api.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;


@WebServlet("/api/musics/*")
public class MusicApiServlet extends HttpServlet {
    private final ObjectMapper mapper = new ObjectMapper();
    private final MusicService musicService = new MusicService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=UTF-8");

        /*if(!"/api/musics".equals(req.getServletPath())){
        resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        mapper.writeValue(resp.getWriter(), new ErrorResponse("GET /api/musics를 사용하세요."));
        return;
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            mapper.writeValue(resp.getWriter(), new ErrorResponse("해당 음악을 찾을 수 없습니다."));
        }*/

        String pathInfo = req.getPathInfo();

        if(pathInfo == null || "/".equals(pathInfo)){
            List<MusicDTO> musics = musicService.findsAllMusics();
            mapper.writeValue(resp.getWriter(), musics);
            return;
        }

        try{
            // "/2" 에서 숫자 추출
            int musicId = Integer.parseInt(pathInfo.substring(1));
            //노래 1곡 찾기
            MusicDTO music = musicService.findMusicById(musicId);

            if(music != null){
                mapper.writeValue(resp.getWriter(), music);
            }else{
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                mapper.writeValue(resp.getWriter(), new ErrorResponse("해당 음악을 찾을 수 없습니다."));
            }
        }catch (NumberFormatException e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(), new ErrorResponse("올바른 음악 ID 형식이 아닙니다."));
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
