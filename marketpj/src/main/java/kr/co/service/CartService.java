package kr.co.service;

import java.util.List;

import kr.co.vo.AttachImageVO;
import kr.co.vo.AuthorVO;
import kr.co.vo.CartDTO;
import kr.co.vo.Criteria;

public interface CartService {
	
	/* īƮ �߰� */
	public int addCart(CartDTO cartDTO);
	
	/* īƮ ���� */
	public int deleteCart(int cartId);
	
	/* īƮ ���� ���� */
	public int modifyCount(CartDTO cartDTO);
	
	/* īƮ ��� */
	public List<CartDTO> getCartList(String memberId);	
	
	/* īƮ Ȯ�� */
	public CartDTO checkCart(CartDTO cartDTO);
}
