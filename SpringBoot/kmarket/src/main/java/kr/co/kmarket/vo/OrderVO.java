package kr.co.kmarket.vo;
/*
 * 날짜: 2023/02/08
 * 이름: 이원정
 * 내용: 상품 ORDER VO
 * */
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderVO {

    private int ordNo;
    private String uid;
    private int ordCount;
    private int ordPrice;
    private int ordDiscount;
    private int ordDelivery;
    private int savePoint;
    private int usedPoint;
    private int ordTotPrice;
    private String recipName;
    private String recipHp;
    private String recipZip;
    private String recipAddr1;
    private String recipAddr2;
    private int ordPayment;
    private int ordComplete;
    private String ordDate;

    // 추가
    private int prodNo;
    private int count;
    private int price;
    private int discount;
    private int point;
    private int delivery;
    private int total;
    private String ordState;
    private String ordStatus;

    public void setProdNo(String prodNo) {
        this.prodNo = Integer.parseInt(prodNo);
    }
}
