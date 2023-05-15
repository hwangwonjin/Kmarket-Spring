package kr.co.kmarket.vo;

/*
 * 날짜: 2023/02/22
 * 이름: 조주영
 * 내용: 마이페이지 My Coupon VO
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyCouponVO {

    private int no;
    private int couponNo;
    private String uid;
    private String exDate;
    private String cutExDate;
    private String status;

    // 추가 필드
    private String couponName;
    private String discountPrice;
    private String limitCondition;


    public String getCutExDate() {
        return exDate.substring(5, 10);
    }


}
