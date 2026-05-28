package com.ohgiraffers.api.likes;

import com.ohgiraffers.api.ErrorResponse;
import com.ohgiraffers.api.likes.dto.LikeRequestDTO;
import com.ohgiraffers.api.likes.dto.LikeResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import com.google.gson.Gson;

import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

@WebServlet("/api/favorites/*")
public class LikeServlet extends HttpServlet {

    private final ObjectMapper mapper = new ObjectMapper();
    private final LikeController likeController = new LikeController();
    private final Gson gson = new Gson();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        String method = req.getMethod();
        String path = req.getPathInfo();

        if (path == null || "/".equals(path)) {
            path = "";
        }

        if (!"".equals(path)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "잘못된 하위 API 경로입니다: " + path);
            return;
        }

        if ("GET".equals(method)) {
            try {
                int userId = Integer.parseInt(req.getParameter("userId"));
                likeController.selectLikesByUser(userId, resp);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "userId 파라미터가 유효한 값이 아닙니다.");
            }
            return;
        }

        if ("POST".equals(method)) {

            LikeResponseDTO responseDTO = null;

            try {
                BufferedReader reader = req.getReader();
                LikeRequestDTO requestDTO = gson.fromJson(reader, LikeRequestDTO.class);

                responseDTO = likeController.addLike(requestDTO);

            } catch (Exception e) {
                e.printStackTrace();
                responseDTO = new LikeResponseDTO();
                responseDTO.setSuccess(false);
                responseDTO.setMessage("서버 내부 파싱 오류가 발생했습니다.");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            }

            if (responseDTO != null) {
                if (!responseDTO.isSuccess()) {
                    if (responseDTO.getLikeId() == -1) {
                        resp.setStatus(HttpServletResponse.SC_CONFLICT); // 409
                    } else {
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
                    }
                } else {
                    resp.setStatus(HttpServletResponse.SC_CREATED); // 201
                }

                PrintWriter out = resp.getWriter();
                out.print(gson.toJson(responseDTO));
                out.flush();
                out.close();
            }
        }

        if ("DELETE".equals(method)) {
            try {
                int likeId = Integer.parseInt(req.getParameter("likeId"));

                Map<String, Object> responseMap = likeController.deleteLike(likeId);

                String jsonResult = mapper.writeValueAsString(responseMap);
                resp.getWriter().print(jsonResult);
                return;

            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().print("{\"success\": false, \"message\": \"유효하지 않은 likeId 형식입니다.\"}");
            } catch (Exception e) {
                e.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().print("{\"success\": false, \"message\": \"서버 내부 오류가 발생했습니다.\"}");
            }
        }

        sendError(resp, SC_NOT_FOUND, "Unknown API path: " + method + " " + path);
    }

    private void sendError(HttpServletResponse resp, int status, String message) throws IOException {
        resp.setStatus(status);
        mapper.writeValue(resp.getWriter(), new ErrorResponse(message));
    }
}
