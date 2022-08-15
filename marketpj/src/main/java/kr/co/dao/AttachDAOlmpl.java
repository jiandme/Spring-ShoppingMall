package kr.co.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.vo.AttachImageVO;
import kr.co.vo.AuthorVO;
import kr.co.vo.Criteria;
import kr.co.vo.MemberVO;

@Repository
public class AttachDAOlmpl implements AttachDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<AttachImageVO> getAttachList(int bookId) {
		return sqlSession.selectList("attachMapper.getAttachList", bookId);
	}
}
