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
@Table(name = "km_product_order_item")
@Builder
public class MyOrderItemEntity {

    // order_item
    @Id
    private int itemNo;

    @Column(name="prodNo", insertable = false, updatable = false)
    private int prodNo;

    @Column(name="ordNo", insertable = false, updatable = false)
    private Long ordNo;
    private String uid;
    private int count;
    private int discount;
    private int price;
    private int point;
    private int delivery;
    private int total;
    private String ordState;
    private int ordStatus;


    @OneToOne
    @JoinColumn(name = "prodNo")
    private MyProdEntity prod;




}
