package com.together.board.service;


import com.together.board.DAO.BoardDAO;
import com.together.board.DAO.ImageDAO;
import com.together.board.bean.BoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.*;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardDAO globalDAO;
    @Autowired
    private ImageDAO imageDAO;
    @Autowired
    private ObjectStorageService objectStorageService;
    @Autowired
    private FileUtils fUtils;
    private String bucketName = "bitcamp-6th-bucket-105";
    @Autowired
    private BoardDAO boardDAO;


    public BoardServiceImpl() {
    }

    public void writeBoard(BoardDTO boardDTO) {
        System.out.println("글작성 서비스 진입");
        System.out.println(boardDTO.toString());
        this.globalDAO.save(boardDTO);
    }

    public Page<BoardDTO> getWriteList(Pageable pageable) {
        Page<BoardDTO> list = this.globalDAO.findAll(pageable);
        return list;
    }

    @Override
    public Map<String, Object> uploadImage(MultipartFile img) throws Exception {
        String filePath = "https://kr.object.ncloudstorage.com";
        //String filePath="/res/board/ctnImg";//이미지가 저장되는 주소(로컬)

        System.out.println("filePath = " +filePath);
        String ctnImg="";

        Map<String, Object> json = new HashMap<>();

        //ctnImg=fUtils.transferTo(img, true, filePath);
        String bucketName="bitcamp-6th-bucket-105";
        String directoryPath="test/";
        ctnImg = objectStorageService.uploadFile(bucketName,directoryPath,img);

        json.put("uploaded", 1);
        json.put("fileName",ctnImg);
        json.put("url", filePath+"/"+bucketName+"/"+directoryPath+ctnImg);


        return json;
    }

    @Override
    public void deleteBoard(BigInteger seq) {
        boardDAO.deleteById(seq);
    }
}
