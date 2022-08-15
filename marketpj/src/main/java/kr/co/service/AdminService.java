package kr.co.service;

import java.util.List;

import kr.co.vo.AttachImageVO;
import kr.co.vo.AuthorVO;
import kr.co.vo.BookVO;
import kr.co.vo.CateVO;
import kr.co.vo.Criteria;
import kr.co.vo.OrderDTO;

public interface AdminService {
	
	/* 책 등록 */
    public void bookEnroll(BookVO bookVO) throws Exception;
    
	/* 카테고리 리스트 */
    public List<CateVO> cateList();	
    
    /* 상품 목록 리스트 */
    public List<BookVO> goodsGetList(Criteria cri);
    
	/* 상품 총 갯수 */
    public int goodsGetTotal(Criteria cri);
    
	/* 상품 상세 정보 */
    public BookVO goodsGetDetail(int bookId);
    
	/* 상품 정보 수정 */
    public int goodsModify(BookVO bookVO);
    
    /* 상품 정보 삭제 */
	public int goodsDelete(int bookId);
	
	/* 이미지 등록 */
	public void imageEnroll(AttachImageVO attachImageVO);
	
	/* 어제자 날짜 이미지 리스트 */
	public List<AttachImageVO> checkFileList();
	
	/* 주문 상품 리스트 */
	public List<OrderDTO> getOrderList(Criteria cri);
	
	/* 주문 총 갯수 */
	public int getOrderTotal(Criteria cri);
}
