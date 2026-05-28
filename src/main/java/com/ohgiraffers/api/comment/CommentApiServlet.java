package com.ohgiraffers.api.comment;

import com.ohgiraffers.api.ErrorResponse;
import com.ohgiraffers.api.music.MusicDTO;
import com.ohgiraffers.api.music.MusicService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;


@WebServlet("/api/comments/*")
public class CommentApiServlet extends HttpServlet {
    private final ObjectMapper mapper = new ObjectMapper();
    private final CommentService commentService = new CommentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=UTF-8");

        String pathInfo = req.getPathInfo();

        String userIdParam = req.getParameter("userId");
        String musicIdParam = req.getParameter("musicId");

        if (userIdParam != null && musicIdParam != null) {
            try {
                int userId = Integer.parseInt(userIdParam);
                int musicId = Integer.parseInt(musicIdParam);

                List<CommentDTO> comments = commentService.findCommentsByUserAndMusic(userId, musicId);
                mapper.writeValue(resp.getWriter(), comments);
                return;

            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                mapper.writeValue(resp.getWriter(), new ErrorResponse("올바른 ID 형식이 아닙니다."));
                return;
            }
        }

        if (pathInfo == null || "/".equals(pathInfo)) {
            List<CommentDTO> comments = commentService.findsAllComments();
            mapper.writeValue(resp.getWriter(), comments);
            return;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
