package kr.co.service;

import java.util.List;

import kr.co.vo.AttachImageVO;
import kr.co.vo.AuthorVO;
import kr.co.vo.CartDTO;
import kr.co.vo.Criteria;

public interface CartService {
	
	/* 카트 추가 */
	public int addCart(CartDTO cartDTO);
	
	/* 카트 삭제 */
	public int deleteCart(int cartId);
	
	/* 카트 수량 수정 */
	public int modifyCount(CartDTO cartDTO);
	
	/* 카트 목록 */
	public List<CartDTO> getCartList(String memberId);	
	
	/* 카트 확인 */
	public CartDTO checkCart(CartDTO cartDTO);
}
