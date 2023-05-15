package kr.co.kmarket.dao;

import kr.co.kmarket.vo.MyPagingVO;
import kr.co.kmarket.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MyDAO {

    // 공통
    public int selectCountOrder(String uid);
    public int selectCountCoupon(String uid);
    public Integer selectSumPoint(String uid);
    public int selectCountCs(String uid);

    //home
    public int insertPoint(MyPointVO vo);
    public List<MyPointVO> selectPoints(String uid);
    public List<MyReviewVO> selectReviews(String uid);
    public List<MyOrderVO> selectOrders(@Param("uid") String uid);
    public int updateOrdStatus(@Param("ordNo") String ordNo, @Param("prodNo") int prodNo);
    public List<MyCsVO> selectCs(String uid);
    public String selectUserType(String uid);
    public MemberVO selectUser(String uid);
    public SellerVO selectSeller(String uid);

    // point
    public int selectPointListCount(String uid);
    public List<MyPointVO> selectPointListByPaging(MyPagingVO dto);
    public List<MyPointVO> selectPointShort1(String uid);
    public List<MyPointVO> selectPointShort2(String uid);
    public List<MyPointVO> selectPointShort3(String uid);

     // home - 최근 주문 내역 - 상품명 선택 시 팝업 창 판매자 정보 출력

    public SellerVO selectCompany (String company);

    // coupon
    public List<MyCouponVO> selectMyCoupons (String uid);
    public int selectCountMyCoupons (String uid);

    // qna
    public CsVO selectQnas (@Param("uid") String uid, @Param("start") int start);
    public int selectCountQnas (String uid);


    
    // home - 최근 주문 내역 - 주문번호 선택 시 팝업 창 주문상세 정보 출력
    public MyOrderVO selectOrderDetails(String ordNo);
    // home - 최근 주문 내역 - 상품명 선택 - 팝업 창 - 문의하기
    public void insertQnaToSeller(CsVO vo);
    // home - 상품평 작성하기
    public int insertReview(MyReviewVO vo);

    // info
    public int updateSellerHp(@Param("hp") String hp, @Param("uid") String uid);
    public int updateUserHp(@Param("hp") String hp, @Param("uid") String uid);
    public int updateSellerEmail(@Param("email") String email, @Param("uid") String uid);
    public int updateUserEmail(@Param("email") String email, @Param("uid") String uid);
    public int updateSellerInfo(@Param("hp") String hp, @Param("email") String email, @Param("zip") String zip, @Param("addr1") String addr1, @Param("addr2") String addr2, @Param("uid") String uid);
    public int updateUserInfo(@Param("hp") String hp, @Param("email") String email, @Param("zip") String zip, @Param("addr1") String addr1, @Param("addr2") String addr2, @Param("uid") String uid);
}
