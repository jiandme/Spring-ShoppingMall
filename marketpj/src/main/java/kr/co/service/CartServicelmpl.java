package kr.co.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.dao.AttachDAO;
import kr.co.dao.AuthorDAO;
import kr.co.dao.CartDAO;
import kr.co.dao.MemberDAO;
import kr.co.vo.AttachImageVO;
import kr.co.vo.AuthorVO;
import kr.co.vo.CartDTO;
import kr.co.vo.Criteria;
import kr.co.vo.MemberVO;

@Service
public class CartServicelmpl implements CartService {
	
	@Autowired
	private CartDAO cartDAO;
	@Autowired
	private AttachDAO attachDAO;
	
	
	@Override
	public int addCart(CartDTO cartDTO) {
		// 장바구니 데이터 체크
		CartDTO checkCart = cartDAO.checkCart(cartDTO);

		if (checkCart != null) {
			return 2;
		}

		// 장바구니 등록 & 에러 시 0반환
		try {
			return cartDAO.addCart(cartDTO);
		} catch (Exception e) {
			return 0;
		}
	}
	
	@Override
	public CartDTO checkCart(CartDTO cartDTO) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int deleteCart(int cartId) {
		return cartDAO.deleteCart(cartId);
	}
	
	@Override
	public List<CartDTO> getCartList(String memberId) {
		
		List<CartDTO> cartList = cartDAO.getCart(memberId);
		
		for(CartDTO cartDTO : cartList) {
			//가격정보 초기화
			cartDTO.initSaleTotal();
			//이미지 정보 얻기
			cartDTO.setImageList(attachDAO.getAttachList(cartDTO.getBookId()));
		}
		
		return cartList;
	}
	
	@Override
	public int modifyCount(CartDTO cartDTO) {
		return cartDAO.modifyCount(cartDTO);
	}
	
		
}
