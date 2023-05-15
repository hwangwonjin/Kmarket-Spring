package kr.co.kmarket.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyOrderVO {
    private String ordNo;
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
    private String ordState;
    private int ordStatus;

    // 추가
    private String recipHp;
    private int stock;

    // order_item
    private int itemNo;
    private int prodNo;
    private int count;
    private int discount;
    private int price;
    private int point;
    private int delivery;
    private int total;

    // product
    private String prodName;
    private String company;
    private String thumb1;
    private int cate1;
    private int cate2;

}
