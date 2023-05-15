package kr.co.kmarket.dao.admin;
/*
 * 날짜: 2023/02/08
 * 이름: 이원정
 * 내용: 관리자 상품 DAO
 */

import kr.co.kmarket.vo.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminProdDAO {
    // ------------------------------------------ 상품 등록 ------------------------------------------
    public void registerProduct(ProductVO vo);
    // --------------------------------  상품 목록1 (판매자 로그인 시) ---------------------------------
    // 상품 목록 조회
    public List<ProductVO> selectProducts(@Param("seller") String seller, @Param("start") int start);

    // -------------------------------- 상품 목록2 (관리자가 조회 시) ------------------------
    public List<ProductVO> selectProductsAdmin(@Param("start") int start);

    // -------------------------------- 관리자 로그인 시 상품 목록  ------------------------------------
    // 상품 목록
    public int updateProduct(ProductVO vo);

    // ------------------------------------------ 페이징 ------------------------------------------
    public int selectCountProduct(@Param("seller") String seller);
    // ------------------------------------------ 상품 삭제 ------------------------------------------
    public int deleteProduct(String prodNo);




}
