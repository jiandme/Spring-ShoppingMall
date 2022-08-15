package kr.co.dao;

import kr.co.vo.MemberVO;

public interface MemberDAO {
	
	//회원가입
	public void memberJoin(MemberVO memberVO);	
	//아이디 중복체크
	public int idCheck(String memberId) throws Exception;
	//로그인
	public MemberVO memberLogin(MemberVO memberVO) throws Exception;
	/* 주문자 주소 정보 */
	public MemberVO getMemberInfo(String memberId);
}
