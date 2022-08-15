package kr.co.dao;

import java.util.List;

import kr.co.vo.AttachImageVO;
import kr.co.vo.AuthorVO;
import kr.co.vo.BookVO;
import kr.co.vo.CartDTO;
import kr.co.vo.Criteria;
import kr.co.vo.MemberVO;
import kr.co.vo.OrderDTO;
import kr.co.vo.OrderItemDTO;
import kr.co.vo.OrderPageItemDTO;

public interface OrderDAO {
	
	/* 주문 상품 정보 */	
	public OrderPageItemDTO getGoodsInfo(int bookId);
	
	/* 주문 상품 정보(주문 처리) */	
	public OrderItemDTO getOrderInfo(int bookId);
	
	/* 주문 테이블 등록 */
	public int enrollOrder(OrderDTO ord);
	
	/* 주문 아이템 테이블 등록 */
	public int enrollOrderItem(OrderItemDTO orderItemDTO);
	
	/* 주문 금액 차감 */
	public int deductMoney(MemberVO memberVO);
	
	/* 주문 재고 차감 */
	public int deductStock(BookVO bookVO);
	
	/* 주문 취소 */
	public int orderCancle(String orderId);
	
	/* 주문 상품 정보(주문취소) */
	public List<OrderItemDTO> getOrderItemInfo(String orderId);
	
	/* 주문 정보(주문취소) */
	public OrderDTO getOrder(String orderId);
	
}
