package com.ohgiraffers.api.likes;

import com.ohgiraffers.api.likes.dto.LikeDTO;
import com.ohgiraffers.api.likes.dto.LikeRequestDTO;
import com.ohgiraffers.api.likes.dto.LikeResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LikeController {

    private final ObjectMapper mapper = new ObjectMapper();
    private final LikeService likeService = new LikeService();

    public void selectLikesByUser(int userId, HttpServletResponse resp) throws IOException {
        try {
            List<LikeDTO> likeList = likeService.selectLikesByUser(userId);

            resp.setStatus(HttpServletResponse.SC_OK);

            String jsonResult = mapper.writeValueAsString(likeList);
            resp.getWriter().print(jsonResult);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "데이터 조회 중 서버 오류가 발생했습니다.");
        }
    }

    public LikeResponseDTO addLike(LikeRequestDTO requestDTO) {

        if (requestDTO == null || requestDTO.getUserId() <= 0 || requestDTO.getMusicId() <= 0) {
            LikeResponseDTO fail = new LikeResponseDTO();
            fail.setSuccess(false);
            fail.setMessage("잘못된 요청 파라미터입니다.");
            return fail;
        }

        return likeService.addLike(requestDTO);

    }

    public Map<String, Object> deleteLike(int likeId) {

        Map<String, Object> resultMap = new HashMap<>();

        int result = likeService.deleteLike(likeId);

        if (result > 0) {
            resultMap.put("status", 200);
            resultMap.put("success", true);
            resultMap.put("message", "좋아요가 해제되었습니다.");
        } else {
            resultMap.put("status", 400);
            resultMap.put("success", false);
            resultMap.put("message", "좋아요 해제에 실패했습니다. (존재하지 않는 내역)");
        }

        return resultMap;
    }
}
