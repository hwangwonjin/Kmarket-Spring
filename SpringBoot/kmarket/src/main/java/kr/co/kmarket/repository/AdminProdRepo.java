package kr.co.kmarket.repository;

import kr.co.kmarket.entity.ProdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
// 2023/02/14 이원정 Admin Product JPA Repository
// find - contains: https://www.memoengine.com/blog/spring-data-jpa-contains-containing-iscontaining/

@Repository
public interface AdminProdRepo extends JpaRepository<ProdEntity, Integer> {
    // <엔티티, PK로 지정한 컬럼의 데이터 타입>
    List<ProdEntity> findByProdNameContains(String prodName);


    //@Query("select pe from ProdEntity as pe")
    //public List<ProdEntity> selectProducts();

}
