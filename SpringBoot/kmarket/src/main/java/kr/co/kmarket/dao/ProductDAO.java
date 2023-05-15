package kr.co.kmarket.dao;

import kr.co.kmarket.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductDAO {

    // cate별 상품리스트 조회하기
    public List<ProductVO> selectProducts(@Param("cate1") String cate1, @Param("cate2") String cate2, @Param("start") int start, @Param("sort") String sort);

    // 검색 상품리스트 조회하기
   // public List<ProductVO> selectSearchProducts()

    // 상품번호로 상품 조회하기
    public ProductVO selectProduct(int prodNo);

    // 상품 네비게이션
    public CateVO selectNavCate(@Param("cate1") String cate1, @Param("cate2") String cate2);

    // 페이징처리
    public int getTotalCount(@Param("cate1") String cate1, @Param("cate2") String cate2);

    // 검색 페이징처리
    //public int selectSearchProduct(@Param("keyword") String keyword);

    // 리뷰 리스트 조회
    public List<MyReviewVO> selectReviewListByPaging(@Param("prodNo") int prodNo, @Param("start") int start);

    // 리뷰 페이징 처리
    public int selectReviewListCount(@Param("prodNo") int prodNo);

    // 장바구니 등록
    public int insertCart(ProductCartVO vo);

    //장바구니 목록
    public List<ProductVO> selectCart(String uid);

    // 장바구니 전체 상품 가격
    public ProductVO selectTotalCart(@Param("uid") String uid);

    // 장바구니 선택 삭제
    public int deleteCart(@Param("checkList") List<String> checkList, @Param("uid") String uid);

    // 장바구니 선택 주문
    public List<ProductVO> selectCartOrder(@Param("checkList") List<String> checkList, @Param("uid") String uid);

    // view에서 주문하기
    public List<ProductVO> selectOrder(@Param("checkList") List<String> checkList);

    // 주문하기 등록
    public int insertComplete(OrderVO vo);

    // 주문 상품 삽입
    public int insertCompleteItem(Product_OrderItemVO vo);

    // 장바구니 삭제
    public int deleteCompleteCart(@Param("uid") String uid, @Param("checkList") List<String> checkList);
}
