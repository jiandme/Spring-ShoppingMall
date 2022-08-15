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
	
	/* �ֹ� ��ǰ ���� */	
	public List<OrderPageItemDTO> getGoodsInfo(List<OrderPageItemDTO> orders);
	
	/* �ֹ� */
	public void order(OrderDTO orderDTO);
	
	/* �ֹ� ��� */
	public void orderCancle(OrderCancelDTO dto);
}
