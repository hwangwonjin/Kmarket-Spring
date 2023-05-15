package kr.co.kmarket.controller.admin;

import kr.co.kmarket.entity.SellerEntity;
import kr.co.kmarket.security.MySellerDetails;
import kr.co.kmarket.service.CsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class AdminController {

    @Autowired
    private CsService service;

    @GetMapping("admin/index")
    public String index(@AuthenticationPrincipal MySellerDetails myUser, Model model){

        SellerEntity seller = myUser.getUser();

        model.addAttribute("seller", seller);
        return "admin/index";
    }


    /*** 고객센터 게시물 단일 삭제 ***/
    @GetMapping("admin/cs/delete")
    public String csDelete(String cate1, String cate2, String type, String pg, int csNo) {
        service.deleteCsArticle(csNo);

        if (type == null) {
            return "redirect:/admin/cs/" + cate1 + "/list?cate2=" + cate2 + "&pg=" + pg;
        } else {
            return "redirect:/admin/cs/" + cate1 + "/list?cate2=" + cate2 + "&type=" + type + "&pg=" + pg;
        }
    }

    /*** 고객센터 게시물 다중 삭제 ***/
    @ResponseBody
    @PostMapping("admin/cs/delete")
    public Map<String, Integer> csDelete(Integer[] valueArr) {

        int size = valueArr.length;
        int result = 0;

        for (int i=0; i<size; i++){
            result = service.deleteCsArticle(valueArr[i]);
        }

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);
        return resultMap;
    }



}
