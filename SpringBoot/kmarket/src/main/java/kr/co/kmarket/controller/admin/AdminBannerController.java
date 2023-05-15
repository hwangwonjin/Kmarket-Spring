package kr.co.kmarket.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminBannerController {

    @GetMapping("admin/config/banner")
    public String banner(){
        return "admin/config/banner";
    }
}
