package kr.co.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.vo.AuthorVO;
import kr.co.vo.BookVO;
import kr.co.vo.CateFilterDTO;
import kr.co.vo.CateVO;
import kr.co.vo.Criteria;
import kr.co.vo.MemberVO;

@Repository
public class BookDAOlmpl implements BookDAO {
	
	@Autowired
	private SqlSession sqlSession;

	
	@Override
	public List<BookVO> goodsGetList(Criteria cri) {
		return sqlSession.selectList("bookMapper.goodsGetList", cri);
	}
	
	@Override
	public int goodsGetTotal(Criteria cri) {
		return sqlSession.selectOne("bookMapper.goodsGetTotal", cri);
	}
	
	@Override
	public List<CateVO> getCateCode1() {
		return sqlSession.selectList("bookMapper.getCateCode1");
	}
	
	@Override
	public List<CateVO> getCateCode2() {
		return sqlSession.selectList("bookMapper.getCateCode2");
	}
	
	@Override
	public List<CateFilterDTO> getCateInfo(Criteria cri) {
		return sqlSession.selectList("bookMapper.getCateInfo", cri);
	}
	
	/*
	 * @Override public String[] getCateList(Criteria cri) { return
	 * sqlSession.selectOne("bookMapper.getCateList", cri); }
	 */
	
	
	@Override
	public BookVO getGoodsInfo(int bookId) {
		return sqlSession.selectOne("bookMapper.getGoodsInfo", bookId);
	}


}
