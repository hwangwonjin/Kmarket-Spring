package kr.co.kmarket.controller;

import kr.co.kmarket.service.IndexService;
import kr.co.kmarket.vo.CateVO;
import kr.co.kmarket.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class MainController {

    @Autowired
    private IndexService service;

    @GetMapping(value = {"/", "index"})
    public String index(Model model){
       // model.addAttribute("category", "main")

        // 카테고리 분류
        Map<String, List<CateVO>> cate = service.selectCates();
        model.addAttribute("cate", cate);

        // index 상품 분류
        Map<String, List<ProductVO>> index = service.selectIndex();
        model.addAttribute("index", index);

        return "index";
    }

}
