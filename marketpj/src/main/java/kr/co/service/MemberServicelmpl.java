package kr.co.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.dao.MemberDAO;
import kr.co.vo.MemberVO;

@Service
public class MemberServicelmpl implements MemberService {
	
	@Autowired
	private MemberDAO dao;
	
	@Override
	public void memberJoin(MemberVO memberVO) {
		dao.memberJoin(memberVO);
		
	}
	
	@Override
	public int idCheck(String memberId) throws Exception {
		return dao.idCheck(memberId);
	}
	
	@Override
	public MemberVO memberLogin(MemberVO memberVO) throws Exception {
		return dao.memberLogin(memberVO);
	}
	
	@Override
	public MemberVO getMemberInfo(String memberId) {
		return dao.getMemberInfo(memberId);
	}
		
}
