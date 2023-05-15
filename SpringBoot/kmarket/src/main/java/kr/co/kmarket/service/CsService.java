package kr.co.kmarket.service;

import kr.co.kmarket.dao.CsDAO;
import kr.co.kmarket.vo.CsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 날짜 : 2023/02/08
 * 이름 : 조주영
 * 내용 : CS 관련 Service
 *
 */


@Service
public class CsService {

    @Autowired
    private CsDAO dao;

    public int insertCsArticle (CsVO vo){
        return dao.insertCsArticle(vo);
    }

    public CsVO selectCsArticle (int csNo) {
        return dao.selectCsArticle(csNo);
    }

    /*** cate1, cate2에 속하는 CS 게시물을 불러오기 ***/
    public List<CsVO> selectCsArticles (String cate1, String cate2, int start) {
        return dao.selectCsArticles(cate1, cate2, start);
    }
    
    /*** 자주묻는 질문 카테고리 불러오기 ***/
    public List<CsVO> selectFaqArticles (String cate2) {
        return dao.selectFaqArticles(cate2);
    }

    /*** 자주묻는 질문 카테고리별 게시물 불러오기 ***/
    public List<CsVO> selectTypeArticles (String cate1, String cate2, String type, int start) {
        return dao.selectTypeArticles(cate1, cate2, type, start);
    }

    /*** cate1에 속하는 모든 CS 게시물을 불러오기 ***/
    public List<CsVO> selectCSArticlesAll (String cate1, int start) {
        return dao.selectCsArticlesAll(cate1, start);
    }

    public int updateCsArticle (CsVO vo) { return dao.updateCsArticle(vo); }
    public int updateCsComment (String comment, int csNo) { return dao.updateCsComment(comment, csNo); }

    public int deleteCsArticle (int csNo) { return dao.deleteCsArticle(csNo); }

    /*** 페이지 시작 값 ***/
    public int getLimitstart(int currentPage) {
        return (currentPage - 1) * 10;
    }

    /*** 현재 페이지 ***/
    public int getCurrentPage(String pg) {
        int currentPage = 1;

        if(pg != null){
            currentPage = Integer.parseInt(pg);
        }
        return currentPage;
    }

    /*** 전체 게시물 갯수 ***/
    public long getTotalCount(String cate1, String cate2, String type) {
        if ("all".equals(cate2)){
            return dao.selectCountTotalAll(cate1);
        } else if (type == null) {
            return dao.selectCountTotal(cate1, cate2);
        } else {
            return dao.selectCountTotalType(cate1, cate2, type);
        }
    }

    /*** 마지막 페이지 번호 ***/
    public int getLastPageNum(long total) {
        int lastPage = 0;

        if(total % 10 == 0) {
            lastPage = (int)(total / 10);
        } else {
            lastPage = (int)(total / 10) + 1;
        }

        return lastPage;
    }

    /*** 페이지 시작 번호 ***/
    public int getPageStartNum(long total, int start) {
        return (int) (total - start);
    }

    /*** 페이지 그룹 ***/
    public int[] getPageGroup(int currentPage, int lastPage) {
        int groupCurrent = (int) Math.ceil(currentPage / 10.0);
        int groupStart = (groupCurrent - 1) * 10 + 1;
        int groupEnd = groupCurrent * 10;

        if(groupEnd > lastPage) {
            groupEnd = lastPage;
        }

        int[] groups = {groupStart, groupEnd};
        return groups;
    }


}
