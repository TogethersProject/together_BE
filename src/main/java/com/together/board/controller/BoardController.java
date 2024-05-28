package com.together.board.controller;

import com.together.board.bean.BoardDTO;
import com.together.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(
        path = {"board"}
)
public class BoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping(
            path = {"writeBoard"}
    )
    public void writeBoard(@ModelAttribute BoardDTO boardDTO) {
        System.out.println("글작성 컨트롤러 진입");

        try {
            this.boardService.writeBoard(boardDTO);
        } catch (Exception var3) {
            System.out.println("error/404");
        }

    }

    @PostMapping(
            path = {"getWriteList"}
    )
    public Page<BoardDTO> getWriteList(@PageableDefault(page = 0,size = 5,sort = {"seq"},direction = Sort.Direction.DESC) Pageable pageable) {
        System.out.println("글목록 출력 컨트롤러");
        Page<BoardDTO> list = this.boardService.getWriteList(pageable);
        return list;
    }

    @PostMapping(path={"writeImage"})
    public Map<String, Object> writeImage(@RequestParam("upload") MultipartFile file) throws Exception {
        return boardService.uploadImage(file);
    }

    //글 삭제. 이미지 삭제는 안 됨.
    @PostMapping(path={"deleteBoard"})
    public void deleteBoard(@RequestParam("seq") String seq){
        BigInteger seqInt = new BigInteger(seq);
        //System.out.println("delete controller: "+seq);
        boardService.deleteBoard(seqInt);
    }
}

