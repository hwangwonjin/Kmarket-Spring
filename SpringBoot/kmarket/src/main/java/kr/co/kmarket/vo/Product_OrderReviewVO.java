package kr.co.kmarket.vo;
/*
 * 날짜: 2023/02/08
 * 이름: 이원정
 * 내용: 상품 REVIEW VO
 */
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product_OrderReviewVO {

    private int revNo;
    private int prodNo;
    private String uid;
    private String content;
    private int rating;
    private String regip;
    private String rdate;

}
