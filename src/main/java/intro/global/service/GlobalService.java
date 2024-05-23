package intro.global.service;

import intro.global.bean.GlobalIntroDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GlobalService {
    void writeGlobal(GlobalIntroDTO globalIntroDTO);
    Page<GlobalIntroDTO> getWriteList(Pageable pageable);
}
