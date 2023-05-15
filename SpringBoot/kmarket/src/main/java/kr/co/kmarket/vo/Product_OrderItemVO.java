package kr.co.kmarket.vo;
/*
 * 날짜: 2023/02/08
 * 이름: 이원정
 * 내용: 상품 ORDER ITEM VO
 * */
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product_OrderItemVO {

    private int ordNo;
    private int prodNo;
    private int count;
    private int price;
    private int discount;
    private int point;
    private int delivery;
    private int total;
    private String ordState;
    private int ordStatus;
    private String uid;
    private String prodName;
    private String descript;
    private String thumb1;
    private String cate1;
    private String cate2;

    public void setProdNo(String prodNo) {
        this.prodNo = Integer.parseInt(prodNo);
    }
}
