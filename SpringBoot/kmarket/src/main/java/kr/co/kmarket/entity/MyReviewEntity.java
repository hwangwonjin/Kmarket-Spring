package kr.co.kmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="km_product_review")
@Builder
public class MyReviewEntity {

    @Id
    private int revNo;

    @Column(name="prodNo", insertable = false, updatable = false)
    private int prodNo;
    private String uid;
    private String content;
    private int rating;
    private String regip;
    private String rdate;
    private String prodName;

    @OneToOne
    @JoinColumn(name = "prodNo")
    private MyProdEntity prod;
}
