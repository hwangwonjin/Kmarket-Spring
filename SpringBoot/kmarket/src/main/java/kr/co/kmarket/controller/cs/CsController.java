package kr.co.kmarket.controller.cs;

/**
 * 날짜 : 2023/02/08
 * 이름 : 조주영
 * 내용 : Cs 메인 컨트롤러
 */

import kr.co.kmarket.service.CsService;
import kr.co.kmarket.vo.CsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CsController {

    @Autowired
    private CsService service;

    @GetMapping("cs/index")
    public String index(Model model) {
        List<CsVO> notices = service.selectCSArticlesAll("notice", 0);
        List<CsVO> qnas = service.selectCSArticlesAll("qna", 0);

        model.addAttribute("notices", notices);
        model.addAttribute("qnas", qnas);

        return "cs/index";
    }

}
