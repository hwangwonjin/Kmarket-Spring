package kr.co.kmarket.controller.cs;

/**
 * 날짜 : 2023/02/09
 * 이름 : 조주영
 * 내용 : Cs board 컨트롤러
 */

import kr.co.kmarket.service.CsService;
import kr.co.kmarket.vo.CsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BoardController {

    @Autowired
    private CsService service;

    @GetMapping("cs/list")
    public String list(Model model, String cate1, String cate2, String pg) {

        List<CsVO> articles = null;                            // view로 연결되는 게시물
        List<String> types = new ArrayList<>();                // faq types
        Map<String, List<CsVO>> faqs = new HashMap<>();        // Map <type, type별 게시물>

        if (pg == null){
            pg = "1";
        }

        /*** 페이징 처리 ***/
        int currentPage = service.getCurrentPage(pg);
        int start = service.getLimitstart(currentPage);
        long total = service.getTotalCount(cate1, cate2, null);
        int lastPage = service.getLastPageNum(total);
        int pageStartNum = service.getPageStartNum(total, start);
        int groups[] = service.getPageGroup(currentPage, lastPage);


        /*** faq 카테고리라면  ***/
        if (cate1.equals("faq")){

            /*** type명 배열 ***/
            List<CsVO> type = service.selectFaqArticles(cate2);
            for (int i=0; i<type.size(); i++){
                types.add(type.get(i).getType());
            }

            /*** type에 따른 게시물 list ***/
            for (int j=0; j<type.size(); j++){
                String tName = types.get(j);
                faqs.put(tName, service.selectTypeArticles(cate1, cate2, tName, 0));
            }

        /*** faq 카테고리가 아니라면 ***/
        } else {
            /*** cate2가 all(전체보기) 일때 ***/
            if (cate2.equals("all")) {
                /*** cate1에 속한 모든 게시물 불러오기  ***/
                articles = service.selectCSArticlesAll(cate1, start);
            } else {
                /*** 아닐 경우 cate2에 속하는 게시물만 불러오기 ***/
                articles = service.selectCsArticles(cate1, cate2, start);
            }
        }


        String oriCate1 = cate1;
        cate1 = "_"+cate1;
        model.addAttribute("articles", articles);
        model.addAttribute("types", types);
        model.addAttribute("faqs", faqs);
        model.addAttribute("cate1", cate1);
        model.addAttribute("oriCate1", oriCate1);
        model.addAttribute("cate2", cate2);
        model.addAttribute("pg", pg);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("pageStartNum", pageStartNum);
        model.addAttribute("groups", groups);
        return "cs/board/list";
    }

    @GetMapping("cs/view")
    public String view(Model model, String cate1, String cate2, String pg, int csNo) {

        CsVO art = service.selectCsArticle(csNo);

        String oriCate1 = cate1;
        cate1 = "_"+cate1;
        model.addAttribute("cate1", cate1);
        model.addAttribute("oriCate1", oriCate1);
        model.addAttribute("cate2", cate2);
        model.addAttribute("pg", pg);
        model.addAttribute("csNo", csNo);
        model.addAttribute("art", art);
        return "cs/board/view";
    }

    @GetMapping("cs/write")
    public String write(Model model, String cate1, String cate2, String pg) {
        String oriCate1 = cate1;
        cate1 = "_"+cate1;
        model.addAttribute("cate1", cate1);
        model.addAttribute("oriCate1", oriCate1);
        model.addAttribute("cate2", cate2);
        model.addAttribute("pg", pg);
        return "cs/board/write";
    }

    @PostMapping("cs/write")
    public String write(CsVO vo) {
        vo.setCate1("qna");
        System.out.println("uid : "+ vo.getUid());
        System.out.println("regip : "+ vo.getRegip());
        service.insertCsArticle(vo);
        return MessageFormat.format("redirect:/cs/list?cate1=qna&cate2={0}", vo.getCate2());
    }

}
