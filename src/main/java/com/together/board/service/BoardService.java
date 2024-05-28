package com.together.board.service;

import com.together.board.bean.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.Map;

public interface BoardService {
    void writeBoard(BoardDTO boardDTO);
    Page<BoardDTO> getWriteList(Pageable pageable);

    Map<String, Object> uploadImage(MultipartFile multipartFile) throws Exception;

    void deleteBoard(BigInteger seq);
}
