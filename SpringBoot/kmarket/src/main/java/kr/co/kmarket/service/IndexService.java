package kr.co.kmarket.service;

import kr.co.kmarket.dao.IndexDAO;
import kr.co.kmarket.repository.CateRepo;
import kr.co.kmarket.vo.CateVO;
import kr.co.kmarket.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class IndexService {

    @Autowired
    private IndexDAO dao;

    @Autowired
    private CateRepo repo;

    // cate
    public List<CateVO> selectCate(String cate1){

        log.info("cate1 : "+cate1);

        return dao.selectCate(cate1);
    }

    public List<CateVO> selectCate1(){
        return dao.selectCate1();
    }

    // index aside 카테고리
    public Map<String, List<CateVO>> selectCates(){
        List<CateVO> cate = dao.selectCates();
        return cate.stream().collect(Collectors.groupingBy(CateVO::getCate1));
    }

    // index 상품 분류
    public Map<String, List<ProductVO>> selectIndex(){
        List<ProductVO> index = dao.selectIndex();
        return index.stream().collect(Collectors.groupingBy(ProductVO::getType));
    }
}
