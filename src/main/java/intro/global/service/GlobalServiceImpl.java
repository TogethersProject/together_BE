package intro.global.service;

import intro.global.DAO.GlobalDAO;
import intro.global.DAO.ImageDAO;
import intro.global.bean.GlobalIntroDTO;
import intro.global.bean.ImageDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class GlobalServiceImpl implements GlobalService{
    @Autowired
    private GlobalDAO globalDAO;
    @Autowired
    private ImageDAO imageDAO;
    @Autowired
    private ObjectStorageService objectStorageService;
    @Autowired
    private FileUtils fUtils;
    private String bucketName = "bitcamp-6th-bucket-105";


    public GlobalServiceImpl() {
    }

    public void writeGlobal(GlobalIntroDTO globalIntroDTO) {
        System.out.println("글작성 서비스 진입");
        System.out.println(globalIntroDTO.toString());
        this.globalDAO.save(globalIntroDTO);
    }

    public Page<GlobalIntroDTO> getWriteList(Pageable pageable) {
        Page<GlobalIntroDTO> list = this.globalDAO.findAll(pageable);
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
}
