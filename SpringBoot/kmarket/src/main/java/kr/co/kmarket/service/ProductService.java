package kr.co.kmarket.service;

import kr.co.kmarket.dao.ProductDAO;
import kr.co.kmarket.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductDAO dao;

    // 상품번호로 상품 조회하기
    public ProductVO selectProduct(int prodNo){
        return dao.selectProduct(prodNo);
    }

    // cate별 상품리스트 조회
    public List<ProductVO> selectProducts(String cate1, String cate2, int start, String sort){
        return dao.selectProducts(cate1, cate2, start, sort);
    }

    // 상품 네비게이션
    public CateVO selectCate(String cate1, String cate2){
        return dao.selectNavCate(cate1, cate2);
    }

    // 페이징처리
    //public int selectCountProduct(String cate1, String cate2){
       //log.info("keyword : " +keyword);

      //  return dao.selectCountProduct(cate1, cate2);
    //}

    // 검사 페이징 처리
    //public int selectSearchProduct(String keyword){
    //    return dao.selectSearchProduct(keyword);
    //}

    // 리뷰 리스트 조회
    public List<MyReviewVO> selectReviewListByPaging(int prodNo, int start){
        return dao.selectReviewListByPaging(prodNo, start);
    }

    //리뷰 페이징 처리
    public int selectReviewListCount(int prodNo){
        return dao.selectReviewListCount(prodNo);
    }

    // 장바구니 등록
    public int insertCart(ProductCartVO vo){
        return dao.insertCart(vo);
    }

    // 장바구니 목록
    public List<ProductVO> selectCart(String uid){
        return dao.selectCart(uid);
    }

    // 장바구니 전체 상품 가격
    public ProductVO selectTotalCart(String uid){
        return dao.selectTotalCart(uid);
    }

    // 장바구니 선택 삭제
    public int deleteCart(List<String> checkList, String uid){

        log.info("checkList : " +checkList);

        return dao.deleteCart(checkList, uid);
    }

    // 장바구니 선택 주문
    public List<ProductVO> selectCartOrder(List<String> checkList, String uid){
        log.info("serCheckList : " + checkList);
        log.info("serUid : " + uid);
        return dao.selectCartOrder(checkList, uid);
    }

    // view에서 주문하기
    public List<ProductVO> selectOrder(List<String> checkList){
        return dao.selectOrder(checkList);
    }

    // 주문하기 등록
    public int insertComplete(OrderVO vo){
        dao.insertComplete(vo);
        return vo.getOrdNo();
    }

    // 주문 상품 등록
    public int insertCompleteItem(Product_OrderItemVO vo){
        log.info("vo : " + vo);
        return dao.insertCompleteItem(vo);
    }

    // 장바구니 주문 완료 상품 삭제
    public int deleteCompleteCart(String uid, List<String> checkList){
        return dao.deleteCompleteCart(uid, checkList);
    }




    // --------------------  페이징  -----------------------
    // 페이지 시작값
    public int getLimitStart(int currentPage) {
        return (currentPage - 1) * 10;
    }

    // 현재 페이지
    public int getCurrentPage(String pg) {
        int currentPage = 1;
        if(pg != null) {
            currentPage = Integer.parseInt(pg);
        }
        return currentPage;
    }

    // 마지막 페이지 번호
    public int getLastPageNum(int total) {

        int lastPage = 0;

        if(total % 10 == 0) {
            lastPage = (int) (total / 10);
        }else {
            lastPage = (int) (total / 10) + 1;
        }

        return lastPage;

    }
    // 전체 개수
    public int getTotalCount(String Cate1, String Cate2) {
        return dao.getTotalCount(Cate1, Cate2);
    }

    // 각 페이지의 시작 번호
    public int getPageStartNum(int total, int start) {
        return (int) (total - start);
    }

    // 페이지 그룹 1그룹(1~10) 2그룹(11~20)
    public int[] getPageGroup(int currentPage, int lastPage) {
        int groupCurrent = (int) Math.ceil(currentPage/10.0);
        int groupStart = (groupCurrent - 1) * 10 + 1;
        int groupEnd = groupCurrent * 10;

        if(groupEnd > lastPage) {
            groupEnd = lastPage;
        }

        int[] groups = {groupStart, groupEnd};

        return groups;
    }
}
