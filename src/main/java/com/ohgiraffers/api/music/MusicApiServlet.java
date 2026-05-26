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

@WebServlet("/api/musics")
public class MusicApiServlet extends HttpServlet {
    private final ObjectMapper mapper = new ObjectMapper();
    private final MusicService musicService = new MusicService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("applicaiton/json; charset=UTF-8");

        if(!"/api/musics".equals(req.getServletPath())){
        resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        mapper.writeValue(resp.getWriter(), new ErrorResponse("GET /api/memos를 사용하세요."));
        return;
        }
        List<MusicDTO> musics = musicService.findsAllMusics();
        mapper.writeValue(resp.getWriter(), musics);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
