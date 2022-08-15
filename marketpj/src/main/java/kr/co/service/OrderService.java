package kr.co.service;

import java.util.List;

import kr.co.vo.AttachImageVO;
import kr.co.vo.AuthorVO;
import kr.co.vo.CartDTO;
import kr.co.vo.Criteria;
import kr.co.vo.OrderCancelDTO;
import kr.co.vo.OrderDTO;
import kr.co.vo.OrderPageItemDTO;

public interface OrderService {
	
	/* 주문 상품 정보 */	
	public List<OrderPageItemDTO> getGoodsInfo(List<OrderPageItemDTO> orders);
	
	/* 주문 */
	public void order(OrderDTO orderDTO);
	
	/* 주문 취소 */
	public void orderCancle(OrderCancelDTO dto);
}
