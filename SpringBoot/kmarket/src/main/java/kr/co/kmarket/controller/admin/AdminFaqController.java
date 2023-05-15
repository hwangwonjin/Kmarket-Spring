package kr.co.kmarket.controller.admin;

import kr.co.kmarket.service.CsService;
import kr.co.kmarket.vo.CsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.MessageFormat;
import java.util.List;

@Slf4j
@Controller
public class AdminFaqController {

    @Autowired
    private CsService service;

    @GetMapping("admin/cs/faq/list")
    public String list(Model model, String cate2, String type, String pg){

        String cate1 = "faq";
        List<CsVO> articles = null;
        long total = 0;

        /*** 페이징 처리 ***/
        int currentPage = service.getCurrentPage(pg);
        int start = service.getLimitstart(currentPage);

        /*** selectbox로 검색한 것이 아닌 경우 ***/
        if ("all".equals(cate2)){
            articles = service.selectCSArticlesAll(cate1, start);
            total = service.getTotalCount(cate1, "all", null);

        /*** 검색어가 있을 경우 ***/
        } else {
            articles = service.selectTypeArticles(cate1, cate2, type, start);
            total = service.getTotalCount(cate1, cate2, type);
        }

        /*** 페이징 처리 ***/
        int lastPage = service.getLastPageNum(total);
        int pageStartNum = service.getPageStartNum(total, start);
        int groups[] = service.getPageGroup(currentPage, lastPage);

        model.addAttribute("articles", articles);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("pageStartNum", pageStartNum);
        model.addAttribute("groups", groups);
        model.addAttribute("cate1", cate1);
        model.addAttribute("cate2", cate2);
        model.addAttribute("type", type);
        model.addAttribute("pg", pg);
        return "admin/cs/faq/list";
    }

    @GetMapping("admin/cs/faq/modify")
    public String modify(Model model, String cate2, String type, int csNo, String pg) {
        CsVO art = service.selectCsArticle(csNo);

        model.addAttribute("art", art);
        model.addAttribute("cate2", cate2);
        model.addAttribute("type", type);
        model.addAttribute("pg", pg);
        model.addAttribute("csNo", csNo);
        return "admin/cs/faq/modify";
    }

    @PostMapping("admin/cs/faq/modify")
    public String modify(int csNo, CsVO vo) {
        service.updateCsArticle(vo);
        return "redirect:/admin/cs/faq/view?pg=1&cate2=all&type=all&csNo="+csNo;
    }

    @GetMapping("admin/cs/faq/view")
    public String view(Model model, String cate2, String type, String pg, int csNo) {

        CsVO art = service.selectCsArticle(csNo);

        model.addAttribute("cate2", cate2);
        model.addAttribute("type", type);
        model.addAttribute("pg", pg);
        model.addAttribute("csNo", csNo);
        model.addAttribute("art", art);
        return "admin/cs/faq/view";
    }

    @GetMapping("admin/cs/faq/write")
    public String write() {return "admin/cs/faq/write";}

    @PostMapping("admin/cs/faq/write")
    public String write(CsVO vo) {
        vo.setCate1("faq");
        service.insertCsArticle(vo);
        return "redirect:/admin/cs/faq/list?cate2=all&type=all&pg=1";
    }

}
