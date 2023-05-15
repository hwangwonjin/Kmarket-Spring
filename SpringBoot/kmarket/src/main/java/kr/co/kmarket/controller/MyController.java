package kr.co.kmarket.controller;

import kr.co.kmarket.entity.MyOrderEntity;
import kr.co.kmarket.entity.MyPointEntity;

import kr.co.kmarket.service.CsService;
import kr.co.kmarket.entity.MyReviewEntity;
import kr.co.kmarket.service.MyService;
import kr.co.kmarket.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Controller
public class MyController {

    @Autowired
    private MyService service;

    @Autowired
    private CsService cService;

    @GetMapping("my/coupon")
    public String coupon(Principal principal, Model model){
        // header part
        int orderCount = service.selectCountOrder(principal.getName());
        int couponCount = service.selectCountCoupon(principal.getName());
        Integer pointSum = service.selectSumPoint(principal.getName());
        int csCount = service.selectCountCs(principal.getName());
        
        /*** 보유 쿠폰 갯수 ***/
        int countCoupon = service.selectCountMyCoupons(principal.getName());

        /*** 쿠폰 상세 현황 ***/
        List<MyCouponVO> coupons = service.selectMyCoupons(principal.getName());

        model.addAttribute("orderCount", orderCount);
        model.addAttribute("couponCount", couponCount);
        model.addAttribute("pointSum", pointSum);
        model.addAttribute("csCount", csCount);
        model.addAttribute("countCoupon", countCoupon);
        model.addAttribute("coupons", coupons);
        return "my/coupon";
    }

    @GetMapping("my/home")
    public String home(Principal principal, Model model){
        List<MyPointVO> pointList = service.selectPoints(principal.getName());
        List<MyReviewVO> reviewList = service.selectReviews(principal.getName());
        List<MyOrderVO> orderList = service.selectOrders(principal.getName());
        List<MyCsVO> csList = service.selectCs(principal.getName());

        String userType = service.selectUserType(principal.getName());

        log.info("userType : " + userType);

        if(userType.equals("seller")){
            SellerVO seller = service.selectSeller(principal.getName());
            model.addAttribute("member", seller);

            log.info("seller name : " + principal.getName());
            log.info("seller member : " + seller);

        }else{
            MemberVO user = service.selectUser(principal.getName());
            model.addAttribute("member", user);

            log.info("user name : " + principal.getName());
            log.info("user member : " + user);
        }

        log.info("pointList : " + pointList);
        log.info("orderList: "+orderList);

        // header part
        int orderCount = service.selectCountOrder(principal.getName());
        int couponCount = service.selectCountCoupon(principal.getName());
        Integer pointSum = service.selectSumPoint(principal.getName());
        int csCount = service.selectCountCs(principal.getName());

        model.addAttribute("pointList", pointList);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("orderList", orderList);
        model.addAttribute("csList", csList);
        model.addAttribute("orderCount", orderCount);
        model.addAttribute("couponCount", couponCount);
        model.addAttribute("pointSum", pointSum);
        model.addAttribute("csCount", csCount);
        return "my/home";
    }

    @GetMapping("my/ordered")
    public String ordered(Principal principal, Model model,
                          @RequestParam(value = "division", required = false, defaultValue = "0") int division,
                          @RequestParam(value = "no", required = false, defaultValue = "0") int no,
                          @RequestParam(value = "begin", required = false, defaultValue = "0") String begin,
                          @RequestParam(value = "end", required = false, defaultValue = "0") String end,
                          @PageableDefault(size = 10, sort = "ordDate", direction = Sort.Direction.DESC) Pageable pageable){
        // header part
        int orderCount = service.selectCountOrder(principal.getName());
        int couponCount = service.selectCountCoupon(principal.getName());
        Integer pointSum = service.selectSumPoint(principal.getName());
        int csCount = service.selectCountCs(principal.getName());

        Page<MyOrderEntity> orderList = null;

        // 기간별 조회
        Calendar cal = Calendar.getInstance();
        if(division == 1){
            if(no == 1){
                cal.add(Calendar.DATE, -7);
            }else if(no == 2){
                cal.add(Calendar.DATE, -15);
            }else{
                cal.add(Calendar.MONTH, -1);
            }
            Date date = new Date(cal.getTimeInMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String newdate = sdf.format(date);
            log.info("date : "+date);
            orderList = service.findMyOrderEntityByUidAndDate1(principal.getName(), newdate, pageable);
        }else if(division == 2){
            if(no == 1){
                cal.add(Calendar.MONTH, -1);
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            } else if (no == 2) {
                cal.add(Calendar.MONTH, -2);
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            } else if (no == 3) {
                cal.add(Calendar.MONTH, -3);
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            } else if (no == 4) {
                cal.add(Calendar.MONTH, -4);
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            } else{
                cal.add(Calendar.MONTH, -5);
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            }
            Date date = new Date(cal.getTimeInMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sta = new SimpleDateFormat("yyyy-MM-01");
            String startdate = sta.format(date);
            String enddate = sdf.format(date);
            log.info("startdate : "+startdate);
            log.info("enddate : "+enddate);
            orderList = service.findMyOrderEntityByUidAndDate2(principal.getName(), startdate, enddate, pageable);
        }else if(division == 3){
            orderList = service.findMyOrderEntityByUidAndDate2(principal.getName(), begin, end, pageable);
        } else{orderList = service.findMyOrderEntityByUid(principal.getName(), pageable);}

        int start = (int)(Math.floor(orderList.getNumber() / 5)*5+1);
        log.info("orderList : "+ orderList.getContent());

        model.addAttribute("orderCount", orderCount);
        model.addAttribute("couponCount", couponCount);
        model.addAttribute("pointSum", pointSum);
        model.addAttribute("csCount", csCount);
        model.addAttribute("orderList", orderList.getContent());
        model.addAttribute("page", orderList);
        model.addAttribute("start", start);
        model.addAttribute("division", division);
        model.addAttribute("no", no);
        return "my/ordered";
    }

    @GetMapping("my/point")
    public String point(Principal principal, Model model,
                        @RequestParam(value = "division", required = false, defaultValue = "0") int division,
                        @RequestParam(value = "no", required = false, defaultValue = "0") int no,
                        @RequestParam(value = "begin", required = false, defaultValue = "0") String begin,
                        @RequestParam(value = "end", required = false, defaultValue = "0") String end,
                        @PageableDefault(size = 10, sort = "pointDate", direction = Sort.Direction.DESC) Pageable pageable){
        // header part
        int orderCount = service.selectCountOrder(principal.getName());
        int couponCount = service.selectCountCoupon(principal.getName());
        Integer pointSum = service.selectSumPoint(principal.getName());
        int csCount = service.selectCountCs(principal.getName());

        Page<MyPointEntity> pointList = null;

        // 기간별 조회
        Calendar cal = Calendar.getInstance();
        if(division == 1){
            if(no == 1){
                cal.add(Calendar.DATE, -7);
            }else if(no == 2){
                cal.add(Calendar.DATE, -15);
            }else{
                cal.add(Calendar.MONTH, -1);
            }
                Date date = new Date(cal.getTimeInMillis());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String newdate = sdf.format(date);
                log.info("date : "+date);
                pointList = service.findByUidAndPointDate1(principal.getName(), newdate, pageable);
        }else if(division == 2){
            if(no == 1){
                cal.add(Calendar.MONTH, -1);
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            } else if (no == 2) {
                cal.add(Calendar.MONTH, -2);
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            } else if (no == 3) {
                cal.add(Calendar.MONTH, -3);
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            } else if (no == 4) {
                cal.add(Calendar.MONTH, -4);
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            } else{
                cal.add(Calendar.MONTH, -5);
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            }
                Date date = new Date(cal.getTimeInMillis());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sta = new SimpleDateFormat("yyyy-MM-01");
                String startdate = sta.format(date);
                String enddate = sdf.format(date);
                log.info("startdate : "+startdate);
                log.info("enddate : "+enddate);
                pointList = service.findByUidAndPointDate2(principal.getName(), startdate, enddate, pageable);
        }else if(division == 3){
            pointList = service.findByUidAndPointDate2(principal.getName(), begin, end, pageable);
        } else{pointList = service.findByUid(principal.getName(), pageable);}

        // 페이징처리
        int start = (int)(Math.floor(pointList.getNumber() / 5)*5+1);

        log.info("pointList:"+pointList.getContent());
        log.info("pointList:"+pointList.getContent().size());
        log.info("start : "+start);

        model.addAttribute("orderCount", orderCount);
        model.addAttribute("couponCount", couponCount);
        model.addAttribute("pointSum", pointSum);
        model.addAttribute("csCount", csCount);
        model.addAttribute("pointList", pointList.getContent());
        model.addAttribute("page", pointList);
        model.addAttribute("start", start);
        model.addAttribute("division", division);
        model.addAttribute("no", no);

        return "my/point";
    }

    @GetMapping("my/qna")
    public String qna(Principal principal, Model model, String pg){

        // header part
        int orderCount = service.selectCountOrder(principal.getName());
        int couponCount = service.selectCountCoupon(principal.getName());
        Integer pointSum = service.selectSumPoint(principal.getName());
        int csCount = service.selectCountCs(principal.getName());


        /*** 페이징 처리 ***/
        int currentPage = cService.getCurrentPage(pg);
        int start = cService.getLimitstart(currentPage);

        CsVO articles = service.selectQnas(principal.getName(), start);

        int total = service.selectCountQnas(principal.getName());
        int lastPage = cService.getLastPageNum(total);
        int pageStartNum = cService.getPageStartNum(total, start);
        int groups[] = cService.getPageGroup(currentPage, lastPage);

        model.addAttribute("orderCount", orderCount);
        model.addAttribute("couponCount", couponCount);
        model.addAttribute("pointSum", pointSum);
        model.addAttribute("csCount", csCount);
        model.addAttribute("articles", articles);
        model.addAttribute("pg", pg);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("pageStartNum", pageStartNum);
        model.addAttribute("groups", groups);

        return "my/qna";
    }

    @GetMapping("my/review")
    public String review(Principal principal, Model model, Pageable pageable){
        // header part
        int orderCount = service.selectCountOrder(principal.getName());
        int couponCount = service.selectCountCoupon(principal.getName());
        int pointSum = service.selectSumPoint(principal.getName());
        int csCount = service.selectCountCs(principal.getName());

        Page<MyReviewEntity> reviewList = service.findByUidOrderByRdateDesc(principal.getName(), pageable);
        int start = (int)(Math.floor(reviewList.getNumber() / 5)*5+1);

        log.info("reviewList : "+reviewList.getContent());

        model.addAttribute("orderCount", orderCount);
        model.addAttribute("couponCount", couponCount);
        model.addAttribute("pointSum", pointSum);
        model.addAttribute("csCount", csCount);
        model.addAttribute("reviewList", reviewList.getContent());
        model.addAttribute("page", reviewList);
        model.addAttribute("start", start);
        return "my/review";
    }

    @GetMapping("my/info")
    public String info(Principal principal, Model model){
        // header part
        int orderCount = service.selectCountOrder(principal.getName());
        int couponCount = service.selectCountCoupon(principal.getName());
        int pointSum = service.selectSumPoint(principal.getName());
        int csCount = service.selectCountCs(principal.getName());

        // 개인정보 조회
        String userType = service.selectUserType(principal.getName());

        if(userType.equals("seller")){
            SellerVO seller = service.selectSeller(principal.getName());

            int idx = seller.getEmail().indexOf("@");
            String emailId = seller.getEmail().substring(0, idx);
            String emailDomain = seller.getEmail().substring(idx+1);

            int i2 = seller.getHp().lastIndexOf("-");
            int i = seller.getHp().indexOf("-");
            String hp1 = seller.getHp().substring(0, i);
            String hp2 = seller.getHp().substring(i+1,i2);
            String hp3 = seller.getHp().substring(i2+1);

            model.addAttribute("member", seller);
            model.addAttribute("emailId", emailId);
            model.addAttribute("emailDomain", emailDomain);
            model.addAttribute("hp1", hp1);
            model.addAttribute("hp2", hp2);
            model.addAttribute("hp3", hp3);

            log.info("seller name : " + principal.getName());
            log.info("seller member : " + seller);

        }else{
            MemberVO user = service.selectUser(principal.getName());

            int idx = user.getEmail().indexOf("@");
            String emailId = user.getEmail().substring(0, idx);
            String emailDomain = user.getEmail().substring(idx+1);

            int i2 = user.getHp().lastIndexOf("-");
            int i = user.getHp().indexOf("-");
            String hp1 = user.getHp().substring(0, i);
            String hp2 = user.getHp().substring(i+1,i2);
            String hp3 = user.getHp().substring(i2+1);

            model.addAttribute("member", user);
            model.addAttribute("emailId", emailId);
            model.addAttribute("emailDomain", emailDomain);
            model.addAttribute("hp1", hp1);
            model.addAttribute("hp2", hp2);
            model.addAttribute("hp3", hp3);

            log.info("user name : " + principal.getName());
            log.info("user member : " + user);
        }
        String maskingId = principal.getName().replaceAll("(?<=.{2}).","*");

        model.addAttribute("orderCount", orderCount);
        model.addAttribute("couponCount", couponCount);
        model.addAttribute("pointSum", pointSum);
        model.addAttribute("csCount", csCount);
        model.addAttribute("maskingId", maskingId);
        model.addAttribute("userType", userType);
        model.addAttribute("uid", principal.getName());
        return "my/info";
    }

    @GetMapping("my/orderConfirm")
    public String orderConfirm(String ordNo, int prodNo, int point, Principal principal){
        MyPointVO vo = new MyPointVO();
        vo.setUid(principal.getName());
        vo.setOrdNo(ordNo);
        vo.setPoint(point);
        vo.setPointdes("상품 구매적립");

        log.info("ordNo"+ordNo);
        log.info("point"+point);

        int[] result = service.orderConfirm(ordNo, prodNo, vo);
        return "redirect:/my/home?result="+result[0]+result[1];
    }


    // home - 최근 주문 내역 - 상품명 선택 시 팝업 창 판매자 정보 출력
    @ResponseBody
    @PostMapping("my/company")
    public Map<String, SellerVO> selectCompany(@RequestParam String company){

        SellerVO vo = service.selectCompany(company);

        Map<String, SellerVO> map = new HashMap<>();
        map.put("company", vo);

        return map;
    }
    // home - 최근 주문 내역 - 주문번호 선택 시 팝업 창 주문상세 정보 출력
    @ResponseBody
    @PostMapping("my/orderDetails")
    public Map<String, MyOrderVO> selectOrderDetails(@RequestParam String ordNo){
        MyOrderVO vo = service.selectOrderDetails(ordNo);

        Map<String, MyOrderVO> map = new HashMap<>();
        map.put("ordNo", vo);

        return map;
    }

    // home - 최근 주문 내역 - 상품명 선택 - 팝업 창 - 문의하기
    @PostMapping("my/qnaToSeller")
    public String toSellerQna(CsVO vo, HttpServletRequest req) throws Exception {
        vo.setRegip(req.getRemoteAddr());
        service.insertQnaToSeller(vo);
        return "redirect:/my/home";
    }

    // home - 상품평 작성하기
    @ResponseBody
    @PostMapping("my/insertReview")
    public Map<String, Integer> insertReview(MyReviewVO vo, HttpServletRequest req) throws  Exception{

        log.warn("here11111");

        vo.setRegip(req.getRemoteAddr());
        int result = service.insertReview(vo);

        log.warn("here22222");

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }



    // info - hp 수정
    @ResponseBody
    @PostMapping("my/modifyHp")
    public Map<String, Integer> modifyHp(String hp, String userType, String uid){
        int result = 0;
        if(userType.equals("seller")){
            result = service.updateSellerHp(hp, uid);
        }else{
            result = service.updateUserHp(hp, uid);
        }
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }

    @ResponseBody
    @PostMapping("my/modifyEmail")
    public Map<String, Integer> modifyEmail(String email, String userType, String uid){
        int result = 0;
        if(userType.equals("seller")){
            result = service.updateSellerEmail(email, uid);
        }else{
            result = service.updateUserEmail(email, uid);
        }
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }

    @ResponseBody
    @PostMapping("my/modifyInfo")
    public Map<String, Integer> modifyInfo(String hp, String email, String zip, String addr1, String addr2, String userType, String uid){
        int result = 0;
        if(userType.equals("seller")){
            result = service.updateSellerInfo(hp, email, zip, addr1, addr2, uid);
        }else{
            result = service.updateUserInfo(hp, email, zip, addr1, addr2, uid);
        }
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }

}
