package kr.co.kmarket.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SellerVO {

    private String uid;
    private String pass;
    private int type;
    private int point;
    private int level;
    private String zip;
    private String addr1;
    private String addr2;
    private String company;
    private String ceo;
    private String name;
    private String bizRegNum;
    private String comRegNum;
    private String tel;
    private String hp;
    private String fax;
    private String email;
    private String regip;
    private String sellerorgeneral;
    private String rdate;

}
