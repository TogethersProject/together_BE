package intro.global.controller;

import intro.global.bean.GlobalIntroDTO;
import intro.global.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(
        path = {"global"}
)
public class GlobalController {
    @Autowired
    private GlobalService globalService;

    @PostMapping(
            path = {"writeGlobal"}
    )
    public void writeGlobal(@ModelAttribute GlobalIntroDTO globalIntroDTO) {
        System.out.println("글작성 컨트롤러 진입");

        try {
            this.globalService.writeGlobal(globalIntroDTO);
            System.out.println("redirect:/global/globalList");
        } catch (Exception var3) {
            System.out.println("error/404");
        }

    }

    @PostMapping(
            path = {"getWriteList"}
    )
    public Page<GlobalIntroDTO> getWriteList(@PageableDefault(page = 0,size = 3,sort = {"seq"},direction = Sort.Direction.DESC) Pageable pageable) {
        System.out.println("글목록 출력 컨트롤러");
        Page<GlobalIntroDTO> list = this.globalService.getWriteList(pageable);
        return list;
    }
}

