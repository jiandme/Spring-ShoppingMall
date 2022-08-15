package kr.co.dao;

import java.util.List;

import kr.co.vo.AuthorVO;
import kr.co.vo.BookVO;
import kr.co.vo.CateFilterDTO;
import kr.co.vo.CateVO;
import kr.co.vo.Criteria;

public interface BookDAO {


	/* ��ǰ �˻� */
	public List<BookVO> goodsGetList(Criteria cri);
	
	/* ��ǰ �� ���� */
	public int goodsGetTotal(Criteria cri);
	
	/* ���� ī�װ� ����Ʈ */
	public List<CateVO> getCateCode1();
	
	/* �ܱ� ī�װ� ����Ʈ */
	public List<CateVO> getCateCode2();
	
	/* �˻� ��� ī�װ� ����Ʈ */
	/* public String[] getCateList(Criteria cri); */
	
	/* ī�װ� ����(+�˻���� ����) */
	public List<CateFilterDTO> getCateInfo(Criteria cri);
	
	/* ��ǰ ���� */
	public BookVO getGoodsInfo(int bookId);
	
}
