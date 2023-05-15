package kr.co.kmarket.service;

import kr.co.kmarket.dao.PolicyDAO;
import kr.co.kmarket.vo.TermsPolicyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyService {

    @Autowired
    private PolicyDAO dao;

    public List<TermsPolicyVO> selectPolicies (String cate, int chapter){
        return dao.selectPolicies(cate, chapter);
    }
    public List<TermsPolicyVO> selectPolicyGroups (String cate) { return dao.selectPolicyGroups(cate); }

    public String selectPolicyGroup (int chapterNo) {return dao.selectPolicyGroup(chapterNo);}
}
