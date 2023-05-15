package kr.co.kmarket.vo;
/*
 * 날짜: 2023/02/08
 * 이름: 이원정
 * 내용: 상품 CART VO
 * */
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCartVO {

    private int cartNo;
    private int prodNo;
    private String uid;
    private int count;
    private int price;
    private int discount;
    private int point;
    private int delivery;
    private int total;
    private String rdate;

    private List<String> checkList;
    private String prodName;
    private String descript;
    private String cate1;
    private String cate2;
    private String thumb2;
    private String thumb1;
    private String type;
    private String seller;

}
