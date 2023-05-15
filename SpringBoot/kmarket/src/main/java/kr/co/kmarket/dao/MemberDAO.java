package kr.co.kmarket.dao;

import kr.co.kmarket.vo.MemberVO;
import kr.co.kmarket.vo.SellerVO;
import kr.co.kmarket.vo.TermsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MemberDAO {

    public TermsVO selectTerms();

    public int insertMember(MemberVO vo);
    public int insertSeller(SellerVO vo);

    public int insertUser(@Param("uid") String uid, @Param("type") int type, @Param("sellerorgeneral") String sellerorgeneral);
}
