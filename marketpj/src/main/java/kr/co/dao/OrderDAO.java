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
	
	/* �ֹ� ��ǰ ���� */	
	public OrderPageItemDTO getGoodsInfo(int bookId);
	
	/* �ֹ� ��ǰ ����(�ֹ� ó��) */	
	public OrderItemDTO getOrderInfo(int bookId);
	
	/* �ֹ� ���̺� ��� */
	public int enrollOrder(OrderDTO ord);
	
	/* �ֹ� ������ ���̺� ��� */
	public int enrollOrderItem(OrderItemDTO orderItemDTO);
	
	/* �ֹ� �ݾ� ���� */
	public int deductMoney(MemberVO memberVO);
	
	/* �ֹ� ��� ���� */
	public int deductStock(BookVO bookVO);
	
	/* �ֹ� ��� */
	public int orderCancle(String orderId);
	
	/* �ֹ� ��ǰ ����(�ֹ����) */
	public List<OrderItemDTO> getOrderItemInfo(String orderId);
	
	/* �ֹ� ����(�ֹ����) */
	public OrderDTO getOrder(String orderId);
	
}
