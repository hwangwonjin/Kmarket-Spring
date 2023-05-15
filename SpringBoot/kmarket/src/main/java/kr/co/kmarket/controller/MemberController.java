package kr.co.kmarket.controller;

import kr.co.kmarket.service.MemberService;
import kr.co.kmarket.vo.MemberVO;
import kr.co.kmarket.vo.SellerVO;
import kr.co.kmarket.vo.TermsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MemberController {

    @Autowired
    private MemberService service;

    @GetMapping("member/login")
    public String login(){
        return "member/login";
    }

    @GetMapping("member/join")
    public String join(){
        return "member/join";
    }

    @GetMapping("member/register")
    public String register(){
        return "member/register";
    }

    @PostMapping("member/register")
    public String register(MemberVO vo, HttpServletRequest req){
        vo.setRegip(req.getRemoteAddr());
        int result = service.insertMember(vo);
        return "redirect:/index?result="+result;
    }

    @GetMapping("member/registerSeller")
    public String registerSeller(){
        return "member/registerSeller";
    }

    @PostMapping("member/registerSeller")
    public String registerSeller(SellerVO vo, HttpServletRequest req){
        vo.setRegip(req.getRemoteAddr());
        int result = service.insertSeller(vo);
        return "redirect:/index?result="+result;
    }


    @GetMapping("member/signup")
    public String signup(Model model, String type){
        TermsVO terms = service.selectTerms();
        model.addAttribute("type", type);
        model.addAttribute("terms", terms);
        return "member/signup";
    }
}
