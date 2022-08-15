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
		/* 사용할 데이터가져오기 */
		/* 회원 정보 */
		MemberVO member = memberDAO.getMemberInfo(orderDTO.getMemberId());
		/* 주문 정보 */
		List<OrderItemDTO> ords = new ArrayList<>();
		for(OrderItemDTO orderItemDTO : orderDTO.getOrders()) {
			OrderItemDTO orderItem = orderDAO.getOrderInfo(orderItemDTO.getBookId());
			// 수량 셋팅
			orderItem.setBookCount(orderItemDTO.getBookCount());
			// 기본정보 셋팅
			orderItem.initSaleTotal();
			//List객체 추가
			ords.add(orderItem);
		}
		/* OrderDTO 셋팅 */
		orderDTO.setOrders(ords);
		orderDTO.getOrderPriceInfo();
		
	/*DB 주문,주문상품(,배송정보) 넣기*/
		
		/* orderId만들기 및 OrderDTO객체 orderId에 저장 */
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("_yyyyMMddmm");
		String orderId = member.getMemberId() + format.format(date);
		orderDTO.setOrderId(orderId);
		
		/* db넣기 */
		orderDAO.enrollOrder(orderDTO);		//book_order 등록
		for(OrderItemDTO orderItemDTO : orderDTO.getOrders()) {		//book_orderItem 등록
			orderItemDTO.setOrderId(orderId);
			orderDAO.enrollOrderItem(orderItemDTO);			
		}

	/* 비용 포인트 변동 적용 */
		
		/* 비용 차감 & 변동 돈(money) Member객체 적용 */
		int calMoney = member.getMoney();
		calMoney -= orderDTO.getOrderFinalSalePrice();
		member.setMoney(calMoney);
		
		/* 포인트 차감, 포인트 증가 & 변동 포인트(point) Member객체 적용 */
		int calPoint = member.getPoint();
		calPoint = calPoint - orderDTO.getUsePoint() + orderDTO.getOrderSavePoint();	// 기존 포인트 - 사용 포인트 + 획득 포인트
		member.setPoint(calPoint);
			
		/* 변동 돈, 포인트 DB 적용 */
		orderDAO.deductMoney(member);
		
	/* 재고 변동 적용 */
		for(OrderItemDTO oit : orderDTO.getOrders()) {
			/* 변동 재고 값 구하기 */
			BookVO bookVO = bookDAO.getGoodsInfo(oit.getBookId());
			bookVO.setBookStock(bookVO.getBookStock() - oit.getBookCount());
			/* 변동 값 DB 적용 */
			orderDAO.deductStock(bookVO);
		}			
		
	/* 장바구니 제거 */
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
		/* 주문, 주문상품 객체 */
		/*회원*/
			MemberVO memberVO = memberDAO.getMemberInfo(orderCancelDTO.getMemberId());
		/*주문상품*/
			List<OrderItemDTO> orderItemDTOList = orderDAO.getOrderItemInfo(orderCancelDTO.getOrderId());
			for(OrderItemDTO orderItemDTO : orderItemDTOList) {
				orderItemDTO.initSaleTotal();
			}
		/* 주문 */
			OrderDTO orw = orderDAO.getOrder(orderCancelDTO.getOrderId());
			orw.setOrders(orderItemDTOList);
			
			orw.getOrderPriceInfo();
			
	/* 주문상품 취소 DB */
			orderDAO.orderCancle(orderCancelDTO.getOrderId());
			
	/* 돈, 포인트, 재고 변환 */
			/* 돈 */
			int calMoney = memberVO.getMoney();
			calMoney += orw.getOrderFinalSalePrice();
			memberVO.setMoney(calMoney);
			
			/* 포인트 */
			int calPoint = memberVO.getPoint();
			calPoint = calPoint + orw.getUsePoint() - orw.getOrderSavePoint();
			memberVO.setPoint(calPoint);
			
				/* DB적용 */
				orderDAO.deductMoney(memberVO);
				
			/* 재고 */
			for(OrderItemDTO ord : orw.getOrders()) {
				BookVO book = bookDAO.getGoodsInfo(ord.getBookId());
				book.setBookStock(book.getBookStock() + ord.getBookCount());
				orderDAO.deductStock(book);
			}
	}
	
	
		
}
