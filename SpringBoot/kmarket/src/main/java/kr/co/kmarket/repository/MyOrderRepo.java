package kr.co.kmarket.repository;

import kr.co.kmarket.entity.MyOrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MyOrderRepo extends JpaRepository<MyOrderEntity, String> {

    // ordered 페이지
    Page<MyOrderEntity> findByUidOrderByOrdDateDesc(String uid, Pageable pageable);

    @Query(value="select pe from MyOrderEntity pe where pe.uid=:uid AND (pe.ordDate >= :date) order by pe.ordDate desc")
    Page<MyOrderEntity> findByUidAndPointDate1(@Param("uid") String uid, @Param("date") String date, Pageable pageable);

    @Query(value="select pe from MyOrderEntity pe where pe.uid=:uid AND (pe.ordDate >= :startdate) AND (pe.ordDate <= :enddate) order by pe.ordDate desc")
    Page<MyOrderEntity> findByUidAndPointDate2(@Param("uid") String uid, @Param("startdate") String startdate, @Param("enddate") String enddate, Pageable pageable);


}
