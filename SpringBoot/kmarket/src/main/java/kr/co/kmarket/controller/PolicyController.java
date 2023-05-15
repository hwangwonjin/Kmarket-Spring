package kr.co.kmarket.controller;

import kr.co.kmarket.service.PolicyService;
import kr.co.kmarket.vo.TermsPolicyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Slf4j
@Controller
public class PolicyController {

    @Autowired
    private PolicyService service;

    @GetMapping(value = {"temrsPolicy/", "termsPolicy/index"})
    public String index(String cate, Model model) {
        Map<String, List<TermsPolicyVO>> map = new HashMap<>();
        List<Integer> chapters = new ArrayList<>();

        /*** 각 장의 이름 불러오기 ***/
        List<TermsPolicyVO> chapt = service.selectPolicyGroups(cate);
        for (int i=0; i<chapt.size(); i++){
            chapters.add(chapt.get(i).getChapterNo());
        }

        /*** 각 장의 약관 내용 ***/
        for (int j=0; j<chapt.size(); j++){
            int chapterNo = chapters.get(j);
            String chapter = service.selectPolicyGroup(chapterNo);
            map.put(chapter, service.selectPolicies(cate, chapterNo));
        }

        Map<String, List<TermsPolicyVO>> sortMap = new TreeMap<>(map);

        model.addAttribute("sortMap", sortMap);
        model.addAttribute("map", map);
        model.addAttribute("cate", cate);
        return "termsPolicy/index";
    }

}
