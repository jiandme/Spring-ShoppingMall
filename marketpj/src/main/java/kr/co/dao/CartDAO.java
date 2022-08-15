package kr.co.dao;

import java.util.List;

import kr.co.vo.AttachImageVO;
import kr.co.vo.AuthorVO;
import kr.co.vo.CartDTO;
import kr.co.vo.Criteria;

public interface CartDAO {
	
	/* 카트 추가 */
	public int addCart(CartDTO cartDTO) throws Exception;
	
	/* 카트 삭제 */
	public int deleteCart(int cartId);
	
	/* 카트 수량 수정 */
	public int modifyCount(CartDTO cartDTO);
	
	/* 카트 목록 */
	public List<CartDTO> getCart(String memberId);	
	
	/* 카트 확인 */
	public CartDTO checkCart(CartDTO cartDTO);
	
	/* 카트 제거(주문) */
	public int deleteOrderCart(CartDTO cartDTO);

}
