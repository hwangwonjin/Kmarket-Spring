package kr.co.kmarket.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TermsPolicyVO {

    private int no;
    private String cate;
    private String chapter;
    private int chapterNo;
    private int group;
    private String tit;
    private String content;

}
