package kr.co.dao;

import java.util.List;

import kr.co.vo.AttachImageVO;
import kr.co.vo.AuthorVO;
import kr.co.vo.CartDTO;
import kr.co.vo.Criteria;

public interface CartDAO {
	
	/* īƮ �߰� */
	public int addCart(CartDTO cartDTO) throws Exception;
	
	/* īƮ ���� */
	public int deleteCart(int cartId);
	
	/* īƮ ���� ���� */
	public int modifyCount(CartDTO cartDTO);
	
	/* īƮ ��� */
	public List<CartDTO> getCart(String memberId);	
	
	/* īƮ Ȯ�� */
	public CartDTO checkCart(CartDTO cartDTO);
	
	/* īƮ ����(�ֹ�) */
	public int deleteOrderCart(CartDTO cartDTO);

}
