package kr.co.kmarket.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyCsVO {
    private int csNo;
    private String uid;
    private String cate1;
    private String cate2;
    private String type;
    private String title;
    private String content;
    private String regip;
    private String rdate;
    private int hit;
    private String comment;
}
