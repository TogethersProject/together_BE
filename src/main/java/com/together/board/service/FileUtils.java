package com.together.board.service;

import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Component
public class FileUtils {
    @Autowired
    private ServletContext ctx;

    public void makeFolders(String path) {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public String getBasePath(String... moreFolder) {
        String temp = "";
        for (String s : moreFolder) {
            temp += s;
        }
        String basePath = ctx.getRealPath(temp);
        return basePath;
    }

    // 확장자 얻어오기
    public String getExt(String fileNm) {
        return fileNm.substring(fileNm.lastIndexOf(".") + 1);
    }

    // 랜덤 파일명 리턴
    public String getRandonFileNm(String fileNm) {
        return UUID.randomUUID().toString() + "." + getExt(fileNm);
    }

    // 파일 저장 & 랜덤 파일명 구하기
    public String transferTo(MultipartFile mf, String... target) {
        String fileNm = null;
        String basePath = getBasePath(target);
        makeFolders(basePath);

        try {
            fileNm = getRandonFileNm(mf.getOriginalFilename());
            File file = new File(basePath, fileNm);
            mf.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return fileNm;
    }

    public String transferTo(MultipartFile mf, boolean createThumb, String... target) throws Exception {
        String fileNm = null;
        String basePath = getBasePath(target);//저장된 이미지 파일을 불러옴
        System.out.println("basePath : "+basePath);
        makeFolders(basePath);
        File file;

        try {
            fileNm = getRandonFileNm(mf.getOriginalFilename());
            file = new File(basePath, fileNm);
            mf.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        //if (createThumb) {
            // saveThumb(file, basePath, 600);
            //makeThumbnail(file, basePath, 550);
        //}
        return fileNm;
    }
}
