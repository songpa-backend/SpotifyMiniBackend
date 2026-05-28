package com.ohgiraffers.api.comment;

import com.ohgiraffers.api.ErrorResponse;
import com.ohgiraffers.api.music.MusicApiServlet;
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
        //System.out.println("doPost 호출됨");
        //req (Request, 요청): 브라우저가 나한테 준 것! ➡️ setCharacterEncoding으로 한글 깨짐 없이 안전하게 읽기 위해 씀!
        req.setCharacterEncoding("UTF-8");
        //resp (Response, 응답): 내가 브라우저한테 줄 것! ➡️ setContentType으로 "이거 JSON이고 한글이야"라고 브라우저에게 알려주기 위해 씀!
        resp.setContentType("application/json; charset=UTF-8");

        registComment(req,resp);
    }

    private void registComment(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        //System.out.println("registComment");
        CommentDTO requestComment = mapper.readValue(req.getReader(), CommentDTO.class);

        String content = requestComment.getContent() == null ? " " : requestComment.getContent().trim();
        int music_id =  requestComment.getMusic_id();
        int user_id = requestComment.getUser_id();

        // 1. 댓글 내용 검증
        if(content.isEmpty()){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(), new ErrorResponse("댓글 내용이 필요합니다."));
            return;
        }

        // 2. 음악 ID 검증
        // 만약 프론트가 보낸 music_id가 자바 DTO에 정상적으로 매핑되지 않아 0이 되었다면 차단
        if(music_id == 0) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(), new ErrorResponse("올바르지 않은 music_id(0)가 전달되었습니다. DTO 필드를 확인하세요."));
            return;
        }

        CommentDTO savedComment = commentService.registComment(content, user_id, music_id);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        mapper.writeValue(resp.getWriter(), savedComment);

    }
}
