package kr.co.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.vo.AuthorVO;
import kr.co.vo.Criteria;
import kr.co.vo.MemberVO;

@Repository
public class AuthorDAOlmpl implements AuthorDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void authorEnroll(AuthorVO authorVO) throws Exception {
		sqlSession.insert("authorMapper.authorEnroll", authorVO);
		
	}
	
	@Override
	public List<AuthorVO> authorGetList(Criteria cri) {
		return sqlSession.selectList("authorMapper.authorGetList", cri);
	}
	
	@Override
	public int authorGetTotal(Criteria cri) {
		return sqlSession.selectOne("authorMapper.authorGetTotal", cri);
	}
	
	@Override
	public AuthorVO authorGetDetail(int authorId) {
		return sqlSession.selectOne("authorMapper.authorGetDetail", authorId);
	}
	
	@Override
	public int authorModify(AuthorVO authorVO) {
		return sqlSession.update("authorMapper.authorModify", authorVO);
	}
	
	@Override
	public int authorDelete(int authorId) {
		return sqlSession.delete("authorMapper.authorDelete", authorId);
	}
}
