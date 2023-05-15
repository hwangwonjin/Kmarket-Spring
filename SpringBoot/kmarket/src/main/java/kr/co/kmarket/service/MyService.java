package kr.co.kmarket.service;

import kr.co.kmarket.dao.MyDAO;
import kr.co.kmarket.entity.MyOrderEntity;
import kr.co.kmarket.entity.MyPointEntity;
import kr.co.kmarket.entity.MyReviewEntity;
import kr.co.kmarket.repository.MyOrderRepo;
import kr.co.kmarket.repository.MyPointRepo;
import kr.co.kmarket.repository.MyReviewRepo;
import kr.co.kmarket.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MyService {

    @Autowired
    private MyDAO dao;

    @Autowired
    private MyPointRepo pointRepo;

    @Autowired
    private MyOrderRepo orderRepo;

    @Autowired
    private MyReviewRepo rRepo;


    // 공통
    public int selectCountOrder(String uid){
        return dao.selectCountOrder(uid);
    }

    public int selectCountCoupon(String uid){
        return dao.selectCountCoupon(uid);
    }

    public Integer selectSumPoint(String uid){
        return dao.selectSumPoint(uid);
    }

    public int selectCountCs(String uid){
        return dao.selectCountCs(uid);
    }

    // home
    public List<MyPointVO> selectPoints(String uid){
        return dao.selectPoints(uid);
    }

    public List<MyReviewVO> selectReviews(String uid){
        return dao.selectReviews(uid);
    }

    public List<MyOrderVO> selectOrders(String uid){return dao.selectOrders(uid);}

    @Transactional
    public int[] orderConfirm(String ordNo, int prodNo, MyPointVO vo){
        int result1 = dao.insertPoint(vo);
        int result2 = dao.updateOrdStatus(ordNo, prodNo);
        int result[] = {result1, result2};
        return result;
    }

    public List<MyCsVO> selectCs(String uid){
        return dao.selectCs(uid);
    }

    public String selectUserType(String uid){
        return dao.selectUserType(uid);
    }

    public MemberVO selectUser(String uid){
        return dao.selectUser(uid);
    }
    public SellerVO selectSeller(String uid){
        return dao.selectSeller(uid);
    }

    // point
    public int selectPointListCount(String uid){
        return dao.selectPointListCount(uid);
    }

    public List<MyPointVO> selectPointListByPaging(MyPagingVO vo){
        return dao.selectPointListByPaging(vo);
    }

    public List<MyPointVO> selectPointShort1(String uid){
        return dao.selectPointShort1(uid);
    }
    public List<MyPointVO> selectPointShort2(String uid){
        return dao.selectPointShort2(uid);
    }
    public List<MyPointVO> selectPointShort3(String uid){
        return dao.selectPointShort3(uid);
    }

    public Page<MyPointEntity> findByUid(String uid, Pageable pageable){
        return pointRepo.findByUid(uid, pageable);
    }
    public Page<MyPointEntity> findByUidAndPointDate1(String uid, String date, Pageable pageable){
        return pointRepo.findByUidAndPointDate1(uid, date, pageable);
    }

    public Page<MyPointEntity> findByUidAndPointDate2(String uid, String startdate, String enddate, Pageable pageable){
        return pointRepo.findByUidAndPointDate2(uid, startdate, enddate, pageable);
    }

    // ordered
    public Page<MyOrderEntity> findMyOrderEntityByUid(String uid, Pageable pageable){
        return orderRepo.findByUidOrderByOrdDateDesc(uid, pageable);
    }

    // coupon
    public List<MyCouponVO> selectMyCoupons(String uid){return dao.selectMyCoupons(uid);}
    public int selectCountMyCoupons(String uid){return dao.selectCountMyCoupons(uid);}

    // home - 최근 주문 내역 - 상품명 선택 시 팝업 창 판매자 정보 출력
    public SellerVO selectCompany (String company){
        return dao.selectCompany(company);
    }

    // home - 최근 주문 내역 - 주문번호 선택 시 팝업 창 주문상세 정보 출력
    public MyOrderVO selectOrderDetails(String ordNo){
        return dao.selectOrderDetails(ordNo);
    }
    // home - 최근 주문 내역 - 상품명 선택 - 팝업 창 - 문의하기
    public void insertQnaToSeller(CsVO vo) throws Exception {
        dao.insertQnaToSeller(vo);
    }
    // home - 상품평 작성하기
    public int insertReview(MyReviewVO vo) throws Exception {
        int result = dao.insertReview(vo);
        return result;
    }


    // qna
    public CsVO selectQnas(String uid, int start) {return dao.selectQnas(uid, start);}
    public int selectCountQnas(String uid) {return dao.selectCountQnas(uid);}

    public Page<MyOrderEntity> findMyOrderEntityByUidAndDate1(String uid, String date, Pageable pageable){
        return orderRepo.findByUidAndPointDate1(uid, date, pageable);
    }

    public Page<MyOrderEntity> findMyOrderEntityByUidAndDate2(String uid, String startdate, String enddate, Pageable pageable){
        return orderRepo.findByUidAndPointDate2(uid, startdate, enddate, pageable);
    }


    // review
    public Page<MyReviewEntity> findByUidOrderByRdateDesc(String uid, Pageable pageable){
        return rRepo.findByUidOrderByRdateDesc(uid, pageable);
    }

    // info
    public int updateSellerHp(String hp, String uid){
        return dao.updateSellerHp(hp, uid);
    }
    public int updateUserHp(String hp, String uid){
        return dao.updateUserHp(hp, uid);
    }
    public int updateSellerEmail(String email, String uid){
        return dao.updateSellerEmail(email, uid);
    }
    public int updateUserEmail(String email, String uid){
        return dao.updateUserEmail(email, uid);
    }
    public int updateSellerInfo(String hp, String email, String zip, String addr1, String addr2, String uid){
        return dao.updateSellerInfo(hp, email, zip, addr1, addr2, uid);
    }
    public int updateUserInfo(String hp, String email, String zip, String addr1, String addr2, String uid){
        return dao.updateUserInfo(hp, email, zip, addr1, addr2, uid);
    }
}
