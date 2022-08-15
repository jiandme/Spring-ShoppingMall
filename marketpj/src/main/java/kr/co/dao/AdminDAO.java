package kr.co.dao;

import java.util.List;

import kr.co.vo.AttachImageVO;
import kr.co.vo.AuthorVO;
import kr.co.vo.BookVO;
import kr.co.vo.CateVO;
import kr.co.vo.Criteria;
import kr.co.vo.OrderDTO;

public interface AdminDAO {
	
	/* ��ǰ ��� */
    public void bookEnroll(BookVO bookVO) throws Exception;
    
	/* ī�װ� ����Ʈ */
    public List<CateVO> cateList();
    
	/* ��ǰ ��� ����Ʈ */
    public List<BookVO> goodsGetList(Criteria cri);
    
	/* ��ǰ �� ���� */
    public int goodsGetTotal(Criteria cri);
    
	/* ��ǰ �� ���� */
    public BookVO goodsGetDetail(int bookId);
    
	/* ��ǰ ���� ���� */
    public int goodsModify(BookVO bookVO);
    
    /* ��ǰ ���� ���� */
	public int goodsDelete(int bookId);
	
	/* �̹��� ��� */
	public void imageEnroll(AttachImageVO attachImageVO);
	
	/* ���� ��ǰ �̹��� ��ü ���� */
	public void deleteImageAll(int bookId);
	
	/* ������ ��¥ �̹��� ����Ʈ */
	public List<AttachImageVO> checkFileList();
	
	/* �ֹ� ��ǰ ����Ʈ */
	public List<OrderDTO> getOrderList(Criteria cri);	
	
	/* �ֹ� �� ���� */
	public int getOrderTotal(Criteria cri);
}

