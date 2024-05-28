package intro.global.service;

import intro.global.bean.GlobalIntroDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface GlobalService {
    void writeGlobal(GlobalIntroDTO globalIntroDTO);
    Page<GlobalIntroDTO> getWriteList(Pageable pageable);

    Map<String, Object> uploadImage(MultipartFile multipartFile) throws Exception;
}
