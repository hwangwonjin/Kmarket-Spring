package kr.co.kmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "km_product_order")
@Builder
public class MyOrderEntity {

    @Id
    @Column(name="ordNo", insertable = false, updatable = false)
    private Long ordNo;
    private String uid ;
    private int ordCount;
    private int ordPrice;
    private int ordDiscount;
    private int ordDelivery;
    private int savePoint;
    private int usedPoint;
    private int ordTotPrice;
    private String recipName;
    private String recipZip;
    private String recipAddr1;
    private String recipAddr2;
    private int ordPayment;
    private int ordComplete;
    private String ordDate;

    @OneToMany
    @JoinColumn(name= "ordNo")
    final private List<MyOrderItemEntity> orderItem = new ArrayList<MyOrderItemEntity>();




}
