package kr.co.kmarket.controller.admin;
/*
 * 날짜: 2023/02/08
 * 이름: 이원정
 * 내용: 관리자 상품 컨트롤러
 */

import kr.co.kmarket.DTO.PagingDTO;
import kr.co.kmarket.entity.SellerEntity;
import kr.co.kmarket.security.MySellerDetails;
import kr.co.kmarket.service.IndexService;
import kr.co.kmarket.service.admin.AdminProdService;
import kr.co.kmarket.util.PagingUtil;
import kr.co.kmarket.vo.CateVO;
import kr.co.kmarket.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class AdminProdController {

    @Autowired
    private AdminProdService service;

    @Autowired
    private IndexService inservice;

    //  상품 목록
    @GetMapping("admin/product/list")
    public String list(Model model, String pg, HttpServletRequest req, @AuthenticationPrincipal MySellerDetails sellerDetails) {

        SellerEntity seller = sellerDetails.getUser();

        log.info("seller uid : " + seller.getUid());
        log.info("seller level : " + seller.getLevel());

        String uid = seller.getUid();
        int level = seller.getLevel();

        // 페이징
        PagingDTO paging = new PagingUtil().getPagingDTO(pg, service.selectCountProduct(uid));

        // 판매자, 관리자 구별
        if(level == 7){
            // 관리자 조회 시 전체 상품 목록
            List<ProductVO> products = service.selectProductsAdmin(paging.getStart());
            // 모델 전송
            model.addAttribute("products", products);
            model.addAttribute("paging", paging);
        }else if(level < 7){
            List<ProductVO> products = service.selectProducts(uid, paging.getStart());
            // 모델 전송
            model.addAttribute("products", products);
            model.addAttribute("paging", paging);
        }


        return "admin/product/list";
    }
    // 상품 목록 (키워드 검색)
    @GetMapping("admin/product/search")
    public String search(@RequestParam(value = "keyword") String keyword,
                         @AuthenticationPrincipal MySellerDetails sellerDetails,
                         Model model) {
        SellerEntity seller = sellerDetails.getUser();
        String uid = seller.getUid();

        List<ProductVO> productVOList = service.search(keyword);

        model.addAttribute("products", productVOList);

        return "admin/product/searchList";
    }

    // 상품 등록하기 '화면'
    @GetMapping("admin/product/register")
    public String register(Model model){
        List<CateVO> cates =inservice.selectCate1();
        System.out.println(cates);
        model.addAttribute("cates", cates);

        return "admin/product/register";
    }

    // 상품 등록하기
    @PostMapping("admin/product/register")
    public String register(ProductVO vo, HttpServletRequest req) throws Exception {

        vo.setIp(req.getRemoteAddr());

        log.warn("here1 : " + vo);

        service.registerProduct(vo);

        log.warn("here2 : " + vo);

        return "redirect:/admin/product/register";
    }

    // 2차 카테고리 설정
    @ResponseBody
    @GetMapping("admin/product/select")
    public List<CateVO> select(String cate1){
        List<CateVO> cate2s = inservice.selectCate(cate1);
        return cate2s;
    }

    // 상품 삭제
    @ResponseBody
    @PostMapping("admin/product/oneDelete")
    public Map<String, Object> productOneDelete(@RequestParam("chkNo") String chkNo){
        int result = 0;

        result = service.deleteProduct(chkNo);

        Map<String, Object> map = new HashMap<>();
        map.put("result", result);

        return map;
    }

    // 상품 선택 삭제
    @ResponseBody
    @PostMapping("admin/product/delete")
    public Map<String, Object> productDelete(@RequestParam("valueArr") String[] valueArr){
        log.info("delete Product ...");
        int size = valueArr.length;
        int result = 0;

        for(int i=0; i<size; i++){
            result = service.deleteProduct(valueArr[i]);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("result", result);

        return map;
    }
}
