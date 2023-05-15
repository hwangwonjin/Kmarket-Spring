package kr.co.kmarket.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyReviewVO {

    private int revNo;
    private int prodNo;
    private String uid;
    private String content;
    private int rating;
    private String regip;
    private String rdate;
    private String prodName;
}
