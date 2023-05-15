package kr.co.kmarket.vo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPagingVO {
    private String uid;
    final private int pageSize = 5;

    // 현재페이지, 시작페이지, 페이지당 글개수, 끝페이지, 게시글 총개수, 마지막페이지, SQL쿼리에 쓸 start, end
    private int nowPage, startPage, cntPerPage, endPage, total, lastPage, start, end;

    public MyPagingVO(int total, int nowPage, int cntPerPage, String uid){
        setNowPage(nowPage);
        setCntPerPage(cntPerPage);
        setTotal(total);
        setUid(uid);
        calcLastPage(getTotal(), getCntPerPage());
        calcStartEndPage(getNowPage(), pageSize);
        calcStartEnd(getNowPage(), getCntPerPage());
    }

    // 제일 마지막 페이지 계산
    public void calcLastPage(int total, int cntPerPage){
        if (total % cntPerPage > 0) {
            setLastPage((total / cntPerPage)+1);
        }else{
            setLastPage(total / cntPerPage);
        }
    }

    // 시작, 끝 페이지 계산
    public void calcStartEndPage(int nowPage, int pageSize){
        setEndPage((int)Math.ceil((double)nowPage / (double)pageSize) * pageSize);
        if (getLastPage() < getEndPage()){
            setEndPage(getLastPage());
        }
        // 공식 : 현재페이지 / 페이징의 개수 * 페이징의 개수 + 1;
        if(nowPage % pageSize == 0){
            setStartPage(nowPage / pageSize * pageSize + 1 - pageSize);
        }else{
            setStartPage(nowPage / pageSize * pageSize + 1);
        }
    }

    // DB 쿼리에서 사용할 start, end 값 계산
    public void calcStartEnd(int nowPage, int cntPerPage){
        setEnd(nowPage * cntPerPage);
        setStart((nowPage - 1)*10);
    }
}
