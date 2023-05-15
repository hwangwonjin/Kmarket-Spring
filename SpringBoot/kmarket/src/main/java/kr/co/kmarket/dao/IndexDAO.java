package kr.co.kmarket.dao;

import kr.co.kmarket.vo.CateVO;

import kr.co.kmarket.vo.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface IndexDAO {

    // 관리자 2차 카테고리 설정
    public List<CateVO> selectCate(String cate1);

    // 관리자 1차 카테고리 설정
    public List<CateVO> selectCate1();
    public List<CateVO> selectCategory();

    // index aside 카테고리 분류
    public List<CateVO> selectCates();

    // index 상품분류
    public List<ProductVO> selectIndex();
}
