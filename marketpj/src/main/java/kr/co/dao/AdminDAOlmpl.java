package kr.co.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.vo.AttachImageVO;
import kr.co.vo.AuthorVO;
import kr.co.vo.BookVO;
import kr.co.vo.CateVO;
import kr.co.vo.Criteria;
import kr.co.vo.MemberVO;
import kr.co.vo.OrderDTO;

@Repository
public class AdminDAOlmpl implements AdminDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void bookEnroll(BookVO bookVO) throws Exception {
		sqlSession.insert("adminMapper.bookEnroll", bookVO);
	}
	
	@Override
	public List<CateVO> cateList() {
		return sqlSession.selectList("adminMapper.cateList");
	}
	
	@Override
	public List<BookVO> goodsGetList(Criteria cri) {
		return sqlSession.selectList("adminMapper.goodsGetList", cri);
	}
	
	@Override
	public int goodsGetTotal(Criteria cri) {
		return sqlSession.selectOne("adminMapper.goodsGetTotal", cri);
	}
	
	@Override
	public BookVO goodsGetDetail(int bookId) {
		return sqlSession.selectOne("adminMapper.goodsGetDetail", bookId);
	}
	
	@Override
	public int goodsModify(BookVO bookVO) {
		return sqlSession.update("adminMapper.goodsModify", bookVO);
		
	}
	
	@Override
	public int goodsDelete(int bookId) {
		return sqlSession.delete("adminMapper.goodsDelete", bookId);
	}
	
	@Override
	public void imageEnroll(AttachImageVO attachImageVO) {
		sqlSession.insert("adminMapper.imageEnroll" , attachImageVO);
		
	}
	
	@Override
	public void deleteImageAll(int bookId) {
		sqlSession.delete("adminMapper.deleteImageAll", bookId);
		
	}
	
	@Override
	public List<AttachImageVO> checkFileList() {
		return sqlSession.selectList("adminMapper.checkFileList");
	}
	
	@Override
	public List<OrderDTO> getOrderList(Criteria cri) {
		return sqlSession.selectList("adminMapper.getOrderList", cri);
	}
	
	@Override
	public int getOrderTotal(Criteria cri) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("adminMapper.getOrderTotal", cri);
	}
}
