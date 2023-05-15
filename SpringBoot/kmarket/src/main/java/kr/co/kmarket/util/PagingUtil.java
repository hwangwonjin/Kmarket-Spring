package kr.co.kmarket.util;

import kr.co.kmarket.DTO.PagingDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PagingUtil {

    // --------------------  페이징  -----------------------
    // 페이지 시작값
    public int getLimitStart(int currentPage) {
        return (currentPage - 1) * 10;
    }

    // 현재 페이지
    public int getCurrentPage(String pg) {
        int currentPage = 1;
        if(pg != null) {
            currentPage = Integer.parseInt(pg);
        }
        return currentPage;
    }

    // 마지막 페이지 번호
    public int getLastPageNum(int total) {

        int lastPage = 0;

        if(total % 10 == 0) {
            lastPage = (int) (total / 10);
        }else {
            lastPage = (int) (total / 10) + 1;
        }

        return lastPage;

    }

    // 각 페이지의 시작 번호
    public int getPageStartNum(int total, int start) {
        return (int) (total - start);
    }

    // 페이지 그룹 1그룹(1~10) 2그룹(11~20)
    public int[] getPageGroup(int currentPage, int lastPage) {
        int groupCurrent = (int) Math.ceil(currentPage/10.0);
        int groupStart = (groupCurrent - 1) * 10 + 1;
        int groupEnd = groupCurrent * 10;

        if(groupEnd > lastPage) {
            groupEnd = lastPage;
        }

        int[] groups = {groupStart, groupEnd};

        return groups;
    }

    public PagingDTO getPagingDTO(String pg, int total) {
        int currentPage = getCurrentPage(pg);
        int start = getLimitStart(currentPage);
        int lastPage = getLastPageNum(total);
        int pageStartNum = getPageStartNum(total, start);
        int groups[] = getPageGroup(currentPage, lastPage);

        return new PagingDTO(groups[0], groups[1], currentPage,lastPage,start);
    }


}
