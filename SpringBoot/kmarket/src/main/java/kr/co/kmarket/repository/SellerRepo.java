package kr.co.kmarket.repository;

import kr.co.kmarket.entity.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SellerRepo extends JpaRepository<SellerEntity, String> {


    List<SellerEntity> findByUid(String uid);

    @Query("select se from SellerEntity as se")
    public List<SellerEntity> selectSellers();
}
