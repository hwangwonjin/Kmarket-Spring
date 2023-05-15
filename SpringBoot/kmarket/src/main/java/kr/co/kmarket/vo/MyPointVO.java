package kr.co.kmarket.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPointVO {

    private int pointNo;
    private String uid;
    private String ordNo;
    private int point;
    private String pointDate;
    private String pointdes;
    private String expireDate;
}
