package kr.co.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.dao.AttachDAO;
import kr.co.dao.AuthorDAO;
import kr.co.dao.BookDAO;
import kr.co.dao.CartDAO;
import kr.co.dao.MemberDAO;
import kr.co.dao.OrderDAO;
import kr.co.vo.AttachImageVO;
import kr.co.vo.AuthorVO;
import kr.co.vo.BookVO;
import kr.co.vo.CartDTO;
import kr.co.vo.Criteria;
import kr.co.vo.MemberVO;
import kr.co.vo.OrderCancelDTO;
import kr.co.vo.OrderDTO;
import kr.co.vo.OrderItemDTO;
import kr.co.vo.OrderPageItemDTO;

@Service
public class OrderServicelmpl implements OrderService {
	
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private AttachDAO attachDAO;
	@Autowired
	private MemberDAO memberDAO;
	@Autowired
	private CartDAO cartDAO;
	@Autowired
	private BookDAO bookDAO;
	
	
	@Override
	public List<OrderPageItemDTO> getGoodsInfo(List<OrderPageItemDTO> orders) {
		
		List<OrderPageItemDTO> result = new ArrayList<OrderPageItemDTO>();		
		
		for(OrderPageItemDTO ord : orders) {
			
			OrderPageItemDTO goodsInfo = orderDAO.getGoodsInfo(ord.getBookId());			

			goodsInfo.setBookCount(ord.getBookCount());	
			
			goodsInfo.initSaleTotal();
			
			List<AttachImageVO> imageList = attachDAO.getAttachList(goodsInfo.getBookId());
			
			goodsInfo.setImageList(imageList);
			
			result.add(goodsInfo);			
		}		
		
		return result;
	}
	
	
	@Override
	@Transactional
	public void order(OrderDTO orderDTO) {
		/* ����� �����Ͱ������� */
		/* ȸ�� ���� */
		MemberVO member = memberDAO.getMemberInfo(orderDTO.getMemberId());
		/* �ֹ� ���� */
		List<OrderItemDTO> ords = new ArrayList<>();
		for(OrderItemDTO orderItemDTO : orderDTO.getOrders()) {
			OrderItemDTO orderItem = orderDAO.getOrderInfo(orderItemDTO.getBookId());
			// ���� ����
			orderItem.setBookCount(orderItemDTO.getBookCount());
			// �⺻���� ����
			orderItem.initSaleTotal();
			//List��ü �߰�
			ords.add(orderItem);
		}
		/* OrderDTO ���� */
		orderDTO.setOrders(ords);
		orderDTO.getOrderPriceInfo();
		
	/*DB �ֹ�,�ֹ���ǰ(,�������) �ֱ�*/
		
		/* orderId����� �� OrderDTO��ü orderId�� ���� */
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("_yyyyMMddmm");
		String orderId = member.getMemberId() + format.format(date);
		orderDTO.setOrderId(orderId);
		
		/* db�ֱ� */
		orderDAO.enrollOrder(orderDTO);		//book_order ���
		for(OrderItemDTO orderItemDTO : orderDTO.getOrders()) {		//book_orderItem ���
			orderItemDTO.setOrderId(orderId);
			orderDAO.enrollOrderItem(orderItemDTO);			
		}

	/* ��� ����Ʈ ���� ���� */
		
		/* ��� ���� & ���� ��(money) Member��ü ���� */
		int calMoney = member.getMoney();
		calMoney -= orderDTO.getOrderFinalSalePrice();
		member.setMoney(calMoney);
		
		/* ����Ʈ ����, ����Ʈ ���� & ���� ����Ʈ(point) Member��ü ���� */
		int calPoint = member.getPoint();
		calPoint = calPoint - orderDTO.getUsePoint() + orderDTO.getOrderSavePoint();	// ���� ����Ʈ - ��� ����Ʈ + ȹ�� ����Ʈ
		member.setPoint(calPoint);
			
		/* ���� ��, ����Ʈ DB ���� */
		orderDAO.deductMoney(member);
		
	/* ��� ���� ���� */
		for(OrderItemDTO oit : orderDTO.getOrders()) {
			/* ���� ��� �� ���ϱ� */
			BookVO bookVO = bookDAO.getGoodsInfo(oit.getBookId());
			bookVO.setBookStock(bookVO.getBookStock() - oit.getBookCount());
			/* ���� �� DB ���� */
			orderDAO.deductStock(bookVO);
		}			
		
	/* ��ٱ��� ���� */
		for(OrderItemDTO oit : orderDTO.getOrders()) {
			CartDTO dto = new CartDTO();
			dto.setMemberId(orderDTO.getMemberId());
			dto.setBookId(oit.getBookId());
			
			cartDAO.deleteOrderCart(dto);
		}
		
	}
	
	@Override
	@Transactional
	public void orderCancle(OrderCancelDTO orderCancelDTO) {
		/* �ֹ�, �ֹ���ǰ ��ü */
		/*ȸ��*/
			MemberVO memberVO = memberDAO.getMemberInfo(orderCancelDTO.getMemberId());
		/*�ֹ���ǰ*/
			List<OrderItemDTO> orderItemDTOList = orderDAO.getOrderItemInfo(orderCancelDTO.getOrderId());
			for(OrderItemDTO orderItemDTO : orderItemDTOList) {
				orderItemDTO.initSaleTotal();
			}
		/* �ֹ� */
			OrderDTO orw = orderDAO.getOrder(orderCancelDTO.getOrderId());
			orw.setOrders(orderItemDTOList);
			
			orw.getOrderPriceInfo();
			
	/* �ֹ���ǰ ��� DB */
			orderDAO.orderCancle(orderCancelDTO.getOrderId());
			
	/* ��, ����Ʈ, ��� ��ȯ */
			/* �� */
			int calMoney = memberVO.getMoney();
			calMoney += orw.getOrderFinalSalePrice();
			memberVO.setMoney(calMoney);
			
			/* ����Ʈ */
			int calPoint = memberVO.getPoint();
			calPoint = calPoint + orw.getUsePoint() - orw.getOrderSavePoint();
			memberVO.setPoint(calPoint);
			
				/* DB���� */
				orderDAO.deductMoney(memberVO);
				
			/* ��� */
			for(OrderItemDTO ord : orw.getOrders()) {
				BookVO book = bookDAO.getGoodsInfo(ord.getBookId());
				book.setBookStock(book.getBookStock() + ord.getBookCount());
				orderDAO.deductStock(book);
			}
	}
	
	
		
}
