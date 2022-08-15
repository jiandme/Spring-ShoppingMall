package kr.co.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.vo.AttachImageVO;
import kr.co.vo.AuthorVO;
import kr.co.vo.BookVO;
import kr.co.vo.CartDTO;
import kr.co.vo.Criteria;
import kr.co.vo.MemberVO;
import kr.co.vo.OrderDTO;
import kr.co.vo.OrderItemDTO;
import kr.co.vo.OrderPageItemDTO;

@Repository
public class OrderDAOlmpl implements OrderDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public OrderPageItemDTO getGoodsInfo(int bookId) {
		return sqlSession.selectOne("orderMapper.getGoodsInfo", bookId);
	}
	
	@Override
	public OrderItemDTO getOrderInfo(int bookId) {
		return sqlSession.selectOne("orderMapper.getOrderInfo", bookId);
	}
	
	@Override
	public int enrollOrder(OrderDTO orderDTO) {
		return sqlSession.insert("orderMapper.enrollOrder", orderDTO);
	}
	
	@Override
	public int enrollOrderItem(OrderItemDTO orderItemDTO) {
		return sqlSession.insert("orderMapper.enrollOrderItem", orderItemDTO);
	}
	
	@Override
	public int deductMoney(MemberVO memberVO) {
		return sqlSession.update("orderMapper.deductMoney", memberVO);
	}
	
	@Override
	public int deductStock(BookVO bookVO) {
		return sqlSession.update("orderMapper.deductStock", bookVO);
	}
	
	@Override
	public OrderDTO getOrder(String orderId) {
		return sqlSession.selectOne("orderMapper.getOrder", orderId);
	}
	
	@Override
	public List<OrderItemDTO> getOrderItemInfo(String orderId) {
		return sqlSession.selectList("orderMapper.getOrderItemInfo", orderId);
	}
	
	@Override
	public int orderCancle(String orderId) {
		return sqlSession.update("orderMapper.orderCancle", orderId);
	}
	
}
