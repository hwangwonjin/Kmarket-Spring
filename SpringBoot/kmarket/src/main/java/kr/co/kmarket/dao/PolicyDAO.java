package kr.co.kmarket.dao;

import kr.co.kmarket.vo.TermsPolicyVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PolicyDAO {

    public List<TermsPolicyVO> selectPolicies(@Param("cate") String cate, @Param("chapterNo") int chapterNo);
    public List<TermsPolicyVO> selectPolicyGroups(String cate);
    public String selectPolicyGroup (int chapterNo);

}
