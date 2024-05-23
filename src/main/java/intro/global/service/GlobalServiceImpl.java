package intro.global.service;

import intro.global.DAO.GlobalDAO;
import intro.global.bean.GlobalIntroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GlobalServiceImpl implements GlobalService{
    @Autowired
    private GlobalDAO globalDAO;

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
}
