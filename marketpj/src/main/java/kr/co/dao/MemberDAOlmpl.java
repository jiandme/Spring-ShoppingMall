package kr.co.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.vo.MemberVO;

@Repository
public class MemberDAOlmpl implements MemberDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void memberJoin(MemberVO memberVO) {
		sqlSession.insert("memberMapper.memberJoin", memberVO);
	}
	
	@Override
	public int idCheck(String memberId) throws Exception {
		return sqlSession.selectOne("memberMapper.idCheck", memberId);
	}
	
	@Override
	public MemberVO memberLogin(MemberVO memberVO) throws Exception {
		return sqlSession.selectOne("memberMapper.memberLogin", memberVO);
	}
	
	@Override
	public MemberVO getMemberInfo(String memberId) {
		return sqlSession.selectOne("memberMapper.getMemberInfo", memberId);
	}

	
}
