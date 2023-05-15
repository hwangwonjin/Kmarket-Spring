package kr.co.kmarket.repository;

import kr.co.kmarket.entity.MyReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyReviewRepo extends JpaRepository<MyReviewEntity, Integer> {

    Page<MyReviewEntity> findByUidOrderByRdateDesc(String uid, Pageable pageable);
}
