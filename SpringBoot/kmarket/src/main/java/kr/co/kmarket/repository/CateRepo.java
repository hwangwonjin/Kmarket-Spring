package kr.co.kmarket.repository;

import kr.co.kmarket.entity.CateEntity;
import kr.co.kmarket.vo.CateVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CateRepo extends JpaRepository<CateEntity, Integer> {
    public List<CateVO> findByCate1(int cate1);
}
