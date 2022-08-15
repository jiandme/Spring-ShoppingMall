package kr.co.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.vo.AttachImageVO;
import kr.co.vo.AuthorVO;
import kr.co.vo.CartDTO;
import kr.co.vo.Criteria;
import kr.co.vo.MemberVO;

@Repository
public class CartDAOlmpl implements CartDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int addCart(CartDTO cartDTO) throws Exception {
		return sqlSession.insert("cartMapper.addCart", cartDTO);
	}
	
	@Override
	public CartDTO checkCart(CartDTO cartDTO) {
		return sqlSession.selectOne("cartMapper.checkCart", cartDTO);
	}
	
	@Override
	public int deleteCart(int cartId) {
		return sqlSession.delete("cartMapper.deleteCart", cartId);
	}
	@Override
	public List<CartDTO> getCart(String memberId) {
		return sqlSession.selectList("cartMapper.getCart", memberId);
	}
	@Override
	public int modifyCount(CartDTO cartDTO) {
		return sqlSession.update("cartMapper.modifyCount", cartDTO);
	}
	
	@Override
	public int deleteOrderCart(CartDTO cartDTO) {
		return sqlSession.delete("cartMapper.deleteOrderCart", cartDTO);
	}
	
	
}
