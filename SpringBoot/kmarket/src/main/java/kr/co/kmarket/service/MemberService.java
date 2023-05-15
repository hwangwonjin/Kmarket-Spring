package kr.co.kmarket.service;

import kr.co.kmarket.dao.MemberDAO;
import kr.co.kmarket.vo.MemberVO;
import kr.co.kmarket.vo.SellerVO;
import kr.co.kmarket.vo.TermsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MemberService {

    @Autowired
    private MemberDAO dao;

    @Autowired
    private PasswordEncoder encoder;

    public TermsVO selectTerms(){
        return dao.selectTerms();
    }

    @Transactional
    public int insertMember(MemberVO vo){
        vo.setPass(encoder.encode(vo.getPass()));
        dao.insertUser(vo.getUid(), 1, "general");
        return dao.insertMember(vo);
    }

    @Transactional
    public int insertSeller(SellerVO vo){
        vo.setPass(encoder.encode(vo.getPass()));
        dao.insertUser(vo.getUid(), 2, "seller");
        return dao.insertSeller(vo);
    }
}
